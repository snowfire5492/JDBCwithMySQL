/*
 * CS4350 Lab#4
 * 
 * 
 * Description: create an interface to a database product using JDBC. I chose to use mysql to complete this project.
 * 
 * Author: Eric Schenck
 * 
 * Last Modified: 12/12/2018
 * 
 */


import java.sql.*;
import java.util.Scanner;

public class JDBCwithMySQL {

	
	public static void main(String[] args) {
		
		try {
			
			Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/"
					+ "pomona_transit", "egschenck" , "mysql");
			
			Scanner keyboard = new Scanner(System.in);
			Scanner keyboard2 = new Scanner(System.in);
			
			String startLoc;
			String endLoc;
			
			boolean keepLooping = true;
			
			
			loop: while(keepLooping) {
				
				System.out.println("1.) Display Trip Schedule...");		// do so for given startlocatoin name and dest name and date
				
				System.out.println("2.) Edit Trip Schedule... ");
				System.out.println("3.) Display stops of Trip... ");
				System.out.println("4.) Display weekly schedule... ");
				System.out.println("5.) Add a Driver... ");
				System.out.println("6.) Add a Bus... ");
				System.out.println("7.) Delete a Bus... ");
				System.out.println("8.) Record actual trip data... ");
				System.out.println("0.) Exit System... ");
				
				int input = keyboard.nextInt();
				
				if(input == 1) {
						
					System.out.println("Enter Start Location Name : ");
					
					startLoc = keyboard2.nextLine();
					
					System.out.println("Enter Destination Name : ");
					
					endLoc = keyboard2.nextLine();
					
					Statement test = myConn.createStatement();
					
					ResultSet res = test.executeQuery("select * from tripoffering tt, trip t "
							+ "where tt.TripNumber = t.TripNumber "
							+ "and t.StartLocationName = '" + startLoc + "'"
							+ "and t.DestinationName = '" + endLoc + "'");
					
					
					System.out.println("----------------------------------------------------------------------");
					System.out.println("| ScheduledStartTime  |  ScheduledArrivalTime  |  DriverName  | BusID |");
					System.out.println("----------------------------------------------------------------------");
					while(res.next()) {
						
						System.out.println("|    " + res.getString("ScheduledStartTime") + "           |  "
								+ "       " + res.getString("ScheduledArrivalTime") + "         |   " 
										+ "" + res.getString("DriverName")  + "        |    " + res.getString("BusID") + "  |");
						
					}
					System.out.println("----------------------------------------------------------------------\n");
//					'123 grove st'
//					'1254 south rd'
					
					
				}else if(input == 2) {
					
					System.out.println("1.) Delete trip offering...");
					System.out.println("2.) Add trip offering...");
					System.out.println("3.) Change driver for trip...");
					System.out.println("4.) Change bus for trip...");
					
					int userInput = keyboard.nextInt();
					
					
					switch(userInput) {
					
					case 1:
						System.out.println("Enter trip number : ");
						int tripNum = keyboard.nextInt();
						System.out.println("Enter Date of trip : ");
						String date = keyboard2.nextLine();
						System.out.println("Enter ScheduledStartTime : ");
						String time = keyboard2.nextLine();
						
						Statement trip = myConn.createStatement();
						
						String str = "delete from tripoffering where TripNumber = " + tripNum 
								+ " and Date = '" + date + "' and ScheduledStartTime = '" 
								+ time + "'";
						
						trip.executeUpdate(str);
						
						//8	10/11/18	1:00PM	2:00PM	Dan	1
						
						System.out.println("value deleted ------ \n");
						break;
					case 2:
						
						boolean askAgain = true;
						
						while(askAgain) {
						
						System.out.println("Enter trip number : ");
						int tripNum2 = keyboard.nextInt();
						System.out.println("Enter Date : ");
						String date2 = keyboard2.nextLine();
						System.out.println("Enter ScheduledStartTime : ");
						String startTime = keyboard2.nextLine();
						System.out.println("Enter ScheduledArrivalTime : ");
						String endTime = keyboard2.nextLine();
						System.out.println("Enter DriverName : ");
						String dName = keyboard2.nextLine();
						System.out.println("Enter BusID : ");
						int busNum = keyboard.nextInt();
						
						Statement itrip = myConn.createStatement();
						
						String istr = "INSERT INTO tripoffering (TripNumber, Date, ScheduledStartTime, "
								+ "ScheduledArrivalTime, DriverName, BusID) VALUES ('" + tripNum2 + "', '" 
								+ date2 + "', '" + startTime + "', '" + endTime + "', '" + dName + "', '" + busNum + "')";
						
					
						
						itrip.executeUpdate(istr);
						
						System.out.println("trip inserted ----- ");
						System.out.println("Add another? { y | n } : \n");
						
						String userChar = keyboard2.nextLine();
						
						if (userChar.contains("n")) {
							askAgain = false;
						}
						
						}
						
						break;
						
					case 3:
						
						System.out.println("Enter TripNumber : ");
						int tripNum3 = keyboard.nextInt();
						System.out.println("Enter Date : ");
						String tripDate = keyboard2.nextLine();
						System.out.println("Enter ScheduledStartTime : ");
						String startTime2 = keyboard2.nextLine();
						System.out.println("Enter new DriverName : ");
						String name = keyboard2.nextLine();
						
						Statement utrip = myConn.createStatement();
						
						String ustr = "UPDATE tripoffering SET DriverName = '" + name + "' WHERE ( TripNumber = '" + tripNum3 + "' "
								+ "AND Date = '" + tripDate + "' AND ScheduledStartTime = '" + startTime2 + "')";
						
					
						utrip.executeUpdate(ustr);
						
						System.out.println("driver_name updated --------- \n");
						
						//    7	10/12/18	3:00PM	4:00PM	Manny	2
						break;
						
					case 4:
						
						System.out.println("Enter TripNumber : ");
						int tripNum4 = keyboard.nextInt();
						System.out.println("Enter Date : ");
						String tripDate1 = keyboard2.nextLine();
						System.out.println("Enter ScheduledStartTime : ");
						String startTime3 = keyboard2.nextLine();
						System.out.println("Enter new BusID : ");
						int id = keyboard.nextInt();
						
						Statement u1trip = myConn.createStatement();
						
						String u1str = "UPDATE tripoffering SET BusID = '" + id + "' WHERE ( TripNumber = '" + tripNum4 + "' "
								+ "AND Date = '" + tripDate1 + "' AND ScheduledStartTime = '" + startTime3 + "')";
						
					
						u1trip.executeUpdate(u1str);
						
						System.out.println("bus_id updated --------- \n");
						
						//    7	10/12/18	3:00PM	4:00PM	Manny	2
						
						break;
					}
					
					
					
				}else if(input == 3) {
					System.out.println("Enter TripNumber : ");
					int tripNum4 = keyboard.nextInt();
					System.out.println("Enter StopNumber : ");
					int stopNum = keyboard.nextInt();
					
				
					
					Statement test = myConn.createStatement();
					
					ResultSet res = test.executeQuery("select * from tripstopinfo t, stop s "
							+ "where t.StopNumber = s.StopNumber "
							+ "and t.TripNumber = '" + tripNum4 + "'"
							+ "and t.StopNumber = '" + stopNum + "'");
					
					
					
					
					System.out.println("-------------------------------");
					System.out.println("| StopNumber  |  StopAddress  |");
					System.out.println("-------------------------------");
					while(res.next()) {
						
						System.out.println("|    " + res.getString("StopNumber") + "        | "
								+ " " + res.getString("StopAddress") + "  |");
						
					}
					System.out.println("-------------------------------\n");
					
					// 1	2	123	1hr
				}else if(input == 4) {
					
					System.out.println("Enter DriverName : ");
					String name = keyboard2.nextLine();
					System.out.println("Enter Date : ");
					String date = keyboard2.nextLine();
					
					String[] formating = date.split("/");
					
					formating[1] = "%";
					
					date = String.join("/", formating);
					
				
					
					Statement test = myConn.createStatement();
					
					ResultSet res = test.executeQuery("select * from tripoffering t where t.DriverName = '" + name +"' and t.Date "
							+ "LIKE '"+ date +"'");
					
					
					System.out.println("-------------------------------------------------------------------------------------------");
					System.out.println("| TripNumber  |  Date  |  ScheduledStartTime  | ScheduledArrivalTime | DriverName | BusID |");
					System.out.println("-------------------------------------------------------------------------------------------");
					while(res.next()) {
						
						System.out.println("|    " + res.getString("TripNumber") + "        | "
								+ res.getString("Date") + "  |       " + res.getString("ScheduledStartTime") + "      |     " 
								+ res.getString("ScheduledArrivalTime") + "       |     "  + res.getString("DriverName") 
								+  "    |    " + res.getString("BusID") + "   |");
						
					}
					System.out.println("-----------------------------------------------------------------------------------------\n");
					
					
					
				}else if(input == 5) {
					
					System.out.println("Enter a new Driver : ");
					String name = keyboard2.nextLine();
					System.out.println("Enter DriverTelephoneNumber : ");
					String number = keyboard2.nextLine();
					
					
					Statement itrip = myConn.createStatement();
					
					String istr = "INSERT INTO driver (DriverName, DriverTelephoneNumber) VALUES ('" + name + "', '" 
							+ number + "')";
					
					itrip.executeUpdate(istr);
					
					System.out.println("driver inserted ------ \n");
					

				}else if(input == 6) {
					
					System.out.println("Enter a new BusID : ");
					int id = keyboard.nextInt();
					System.out.println("Enter Model : ");
					String model = keyboard2.nextLine();
					System.out.println("Enter year : ");
					int year = keyboard.nextInt();
					
					Statement itrip = myConn.createStatement();
					
					String istr = "INSERT INTO bus (BusID, Model, Year) VALUES ('" + id + "', '" 
							+ model + "', '" + year + "')";
					
			
					itrip.executeUpdate(istr);
					
					System.out.println("bus inserted ------ \n");
					
					
					
				}else if(input == 7) {
					
					
					System.out.println("Enter BusID : ");
					int id = keyboard.nextInt();
					
					
					Statement trip = myConn.createStatement();
					
					String str = "delete from bus where BusID = '" + id + "'";  
					trip.executeUpdate(str);
					
				
					//6 	11/11/11	11:00PM	

					System.out.println("bus deleted ------ \n");
					
					
					
				}else if(input == 8) {
					
					Statement test = myConn.createStatement();
					
					
					
					System.out.println("Enter TripNumber : ");
					int tripNum = keyboard.nextInt();
					System.out.println("Enter Date : ");
					String date = keyboard2.nextLine();
					System.out.println("Enter ScheduledStartTime : ");
					String startTime = keyboard2.nextLine();
					System.out.println("Enter StopNumber : ");
					int stopNum = keyboard.nextInt();
					
					ResultSet res = test.executeQuery("select * from tripoffering t where t.TripNumber = '" + tripNum +"' and t.Date = '" + date +"'"
							+ " and t.ScheduledStartTime = '" + startTime + "'");
					
					
					String arrival = "";
					
					while(res.next()){
					arrival = res.getString("ScheduledArrivalTime");
					}
					
					System.out.println("Enter ActualStartTime : ");
					String actualStart = keyboard2.nextLine();
					System.out.println("Enter ActualArrivalTime : ");
					String actualArrive = keyboard2.nextLine();
					System.out.println("Enter NumberOfPassengerIn : ");
					int NumPassIn = keyboard.nextInt();
					System.out.println("Enter NumberOfPassengerOut : ");
					int NumPassOut = keyboard.nextInt();
					
					Statement itrip = myConn.createStatement();
					
					String istr = "INSERT INTO actualtripstopinfo (TripNumber, Date, ScheduledStartTime, StopNumber,"
							+ " ScheduledArrivalTime, ActualStartTime, ActualArrivalTime, NumberOfPassengerIn,"
							+ " NumberOfPassengerOut ) VALUES ('" + tripNum + "', '"  + date + "', '" + startTime + "', '" 
							+ stopNum + "', '" + arrival + "', '" + actualStart + "', '" 
							+ actualArrive + "', '" + NumPassIn + "', '" + NumPassOut + "')";
					
					
					
					itrip.executeUpdate(istr);
					
					
					//3	10/11/18	1:00PM	2:00PM	Bob	2 stop#4
					
					System.out.println("Record updated ------ \n");
					
				}else if(input == 0) {
					keepLooping = false;
					break loop;
				}
				
			}
			
		
		}catch( Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
