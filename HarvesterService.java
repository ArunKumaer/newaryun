package com.harvester;

import jakarta.persistence.LockModeType;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class HarvesterService {

	public void save(Object obj) {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			session.persist(obj);
			tx.commit();
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		}
	}


	public List<Farmer> getAllFarmers() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Farmer", Farmer.class).list();
		}
	}

	public List<Harvester> getAvailableHarvesters() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Harvester h where h.available = true", Harvester.class).list();
		}
	}

	public List<Booking> getAllBookings() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Booking", Booking.class).list();
		}
	}

	public void findNearest(int farmerId) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Farmer farmer = session.get(Farmer.class, farmerId);
			if (farmer == null) {
				System.out.println("Farmer not found!");
				return;
			}

			List<Harvester> list = session.createQuery("from Harvester h where h.available = true", Harvester.class)
					.list();

			Harvester nearest = null;
			double minDistance = Double.MAX_VALUE;

			for (Harvester h : list) {
				double d = DistanceUtil.distanceKm(farmer.getLatitude(), farmer.getLongitude(), h.getLatitude(),
						h.getLongitude());
				System.out.printf("  %s is %.1f km away%n", h.getName(), d);
				if (d < minDistance) {
					minDistance = d;
					nearest = h;
				}
			}

			if (nearest == null) {
				System.out.println("No harvester available.");
			} else {
				System.out.printf("NEAREST: %s (ID %d), %.1f km away%n", nearest.getName(), nearest.getId(),
						minDistance);
			}
		}
	}

	public String bookHarvester(int farmerId, int harvesterId, LocalDate date) {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();

			Harvester harvester = session.createQuery("from Harvester h where h.id = :id", Harvester.class)
					.setParameter("id", harvesterId).setLockMode(LockModeType.PESSIMISTIC_WRITE).uniqueResult();

			Farmer farmer = session.get(Farmer.class, farmerId);

			if (farmer == null || harvester == null) {
				tx.rollback();
				return "Farmer or Harvester ID not found!";
			}

			Long count = session.createQuery(
					"select count(b) from Booking b "
							+ "where b.harvester.id = :hid and b.bookingDate = :d and b.status <> 'REJECTED'",
					Long.class).setParameter("hid", harvesterId).setParameter("d", date).uniqueResult();

			if (count > 0) {
				tx.rollback();
				return "Sorry, this harvester is already booked on " + date;
			}

			Booking booking = new Booking(farmer, harvester, date, "PENDING");
			session.persist(booking);
			tx.commit();
			return "Booking created! Booking ID = " + booking.getId() + " (status PENDING)";

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return "Booking failed: " + e.getMessage();
		}
	}

	public String updateBookingStatus(int bookingId, String newStatus) {
		Transaction tx = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			tx = session.beginTransaction();
			Booking booking = session.get(Booking.class, bookingId);
			if (booking == null) {
				tx.rollback();
				return "Booking not found!";
			}
			booking.setStatus(newStatus);
			tx.commit();
			return "Booking " + bookingId + " is now " + newStatus;
		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			return "Update failed: " + e.getMessage();
		}
	}

	public void addSampleDataIfEmpty() {
		if (!getAllFarmers().isEmpty())
			return;

		save(new Farmer("Murugan", "9876543210", "Papanasam", 10.92, 79.27));
		save(new Farmer("Selvi", "9123456780", "Orathanadu", 10.62, 79.24));

		save(new Harvester("Kartar 4000", "Raja", "Paddy", 15, 10.787, 79.139, true));
		save(new Harvester("John Deere", "Kumar", "Paddy", 12, 10.790, 78.705, true));
		save(new Harvester("Preet 987", "Anand", "Wheat", 10, 10.772, 79.636, true));
		System.out.println("Sample data added.");
	}
}
