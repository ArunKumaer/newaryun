package com.harvester;

import java.time.LocalDate;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		HarvesterService service = new HarvesterService();
		Scanner sc = new Scanner(System.in);

		service.addSampleDataIfEmpty();

		while (true) {
			System.out.println("\n------ HARVESTER BOOKING SYSTEM -------");
			System.out.println("1. Add farmer");
			System.out.println("2. Add harvester");
			System.out.println("3. Show farmers");
			System.out.println("4. Show available harvesters");
			System.out.println("5. Find nearest harvester for a farmer");
			System.out.println("6. Book a harvester");
			System.out.println("7. Show all bookings");
			System.out.println("8. Approve / Reject a booking (owner)");
			System.out.println("9. Exit");
			System.out.println("Choose: ");
			String choice = sc.nextLine().trim();

			try {
				switch (choice) {
				case "1":
					System.out.print("Name: ");
					String fn = sc.nextLine();
					System.out.print("Phone: ");
					String ph = sc.nextLine();
					System.out.print("Village: ");
					String vi = sc.nextLine();
					System.out.print("Latitude: ");
					double flat = Double.parseDouble(sc.nextLine());
					System.out.print("Longitude: ");
					double flon = Double.parseDouble(sc.nextLine());
					service.save(new Farmer(fn, ph, vi, flat, flon));
					System.out.println("Farmer saved!");
					break;

				case "2":
					System.out.print("Machine name: ");
					String hn = sc.nextLine();
					System.out.print("Owner name: ");
					String on = sc.nextLine();
					System.out.print("Crop (Paddy/Wheat): ");
					String cr = sc.nextLine();
					System.out.print("Capacity (acres/day): ");
					int cap = Integer.parseInt(sc.nextLine());
					System.out.print("Latitude: ");
					double hlat = Double.parseDouble(sc.nextLine());
					System.out.print("Longitude: ");
					double hlon = Double.parseDouble(sc.nextLine());
					service.save(new Harvester(hn, on, cr, cap, hlat, hlon, true));
					System.out.println("Harvester saved!");
					break;

				case "3":
					service.getAllFarmers().forEach(System.out::println);
					break;

				case "4":
					service.getAvailableHarvesters().forEach(System.out::println);
					break;

				case "5":
					System.out.print("Farmer ID: ");
					service.findNearest(Integer.parseInt(sc.nextLine()));
					break;

				case "6":
					System.out.print("Farmer ID: ");
					int fid = Integer.parseInt(sc.nextLine());
					System.out.print("Harvester ID: ");
					int hid = Integer.parseInt(sc.nextLine());
					System.out.print("Date (yyyy-mm-dd): ");
					LocalDate date = LocalDate.parse(sc.nextLine());
					System.out.println(service.bookHarvester(fid, hid, date));
					break;

				case "7":
					service.getAllBookings().forEach(System.out::println);
					break;

				case "8":
					System.out.print("Booking ID: ");
					int bid = Integer.parseInt(sc.nextLine());
					System.out.print("Status (APPROVED / REJECTED): ");
					String st = sc.nextLine().trim().toUpperCase();
					System.out.println(service.updateBookingStatus(bid, st));
					break;

				case "0":
					System.out.println("Bye!");
					HibernateUtil.getSessionFactory().close();
					return;

				default:
					System.out.println("Wrong choice, try again.");
				}
			} catch (Exception e) {
				System.out.println("Invalid input: " + e.getMessage());
			}
		}
	}
}
