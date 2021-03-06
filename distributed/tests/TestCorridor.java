package se.oru.coordination.coordination_oru.distributed.tests;

import se.oru.coordination.coordination_oru.distributed.models.*;
import se.oru.coordination.coordination_oru.util.BrowserVisualizationDist;


import java.util.*;
import java.util.concurrent.TimeUnit;

import org.metacsp.multi.spatioTemporal.paths.Pose;

public class TestCorridor {

	private static ArrayList<Vehicle> vehicleList = new ArrayList<Vehicle>();
	private static ArrayList<TrafficLights> trafficLightsList = new ArrayList<TrafficLights>();
	private static HashMap<Integer,RobotReport> mainTable = new HashMap<Integer,RobotReport>();
	private static String yamlFile = null;
	
	public static Thread initThread(int id, Vehicle.Category ctg, Pose start, Pose[] goal) {
		Vehicle vehicle = new Vehicle(id, ctg, start, goal, 50, 3, true,yamlFile);
		Thread thread = new Thread(new VehicleThread(vehicle));
		vehicleList.add(vehicle);
		return thread;
	}

	public static void main(String args[]) throws InterruptedException {

		Vehicle.Category a = Vehicle.Category.AMBULANCE;
		Vehicle.Category c = Vehicle.Category.CAR;

		BrowserVisualizationDist viz = new BrowserVisualizationDist();
		viz.setInitialTransform(35, 2, 5);
		
		
		Thread.sleep(3000);

		Pose CorrStart = new Pose(9.0,5.0,0);
		Pose CorrEnd  = new Pose(25.5,5.0,0);
		TrafficLights corridor = new TrafficLights(1, CorrStart, CorrEnd, 2, viz);
		trafficLightsList.add(corridor);

		Thread.sleep(4000);

		Pose start1 = new Pose(2.0,4.0,Math.PI);
		Pose goal11 = new Pose(10.0,6.0,Math.PI);
		Pose goal12 = new Pose(25.0,6.0,Math.PI);
		Pose goal13 = new Pose(35.0,4.0,Math.PI);
		Pose[] goal1 = { goal11, goal12, goal13};
		
		Pose start2 = new Pose(35.0,9.0,0);
		Pose goal21 = new Pose(25.0,6.0,0);
		Pose goal22 = new Pose(10.0,6.0,0);
		Pose goal23 = new Pose(2.0,9.0,0);
		Pose[] goal2 = { goal21, goal22, goal23};
		
		Pose start3 = new Pose(2.0,6.0,Math.PI);
		Pose goal31 = new Pose(10.0,6.0,Math.PI);
		Pose goal32 = new Pose(25.0,6.0,Math.PI);
		Pose goal33 = new Pose(35.0,6.0,Math.PI);
		Pose[] goal3 = { goal31, goal32, goal33};		

		Thread thread1 = initThread(1, a, start1, goal1);
		Thread thread2 = initThread(2, c, start2, goal2);
		Thread thread3 = initThread(3, c, start3, goal3);

		Pose goal43 = new Pose(2.0,4.0,0);
		Pose goal42 = new Pose(10.0,6.0,0);
		Pose goal41 = new Pose(25.0,6.0,0);
		Pose start4 = new Pose(35.0,4.0,0);
		Pose[] goal4 = { goal41, goal42, goal43};

		Pose goal53 = new Pose(2.0,6.0,0);
		Pose goal52 = new Pose(10.0,6.0,0);
		Pose goal51 = new Pose(25.0,6.0,0);
		Pose start5 = new Pose(35.0,6.0,0);
		Pose[] goal5 = { goal51, goal52, goal53};
		
		//Thread thread4 = initThread(4, c, start4, goal4);
		//Thread thread5 = initThread(5, c, start5, goal5);
	
	
	double rMax = -1; double tMax = -1;
	for (Vehicle vh : vehicleList){
		double r = vh.getRadius();
		if (r > rMax) {
			rMax = r;
			tMax = r/vh.getVelMax();
		}
	}
	for (Vehicle vh : vehicleList){
		vh.Init(rMax, tMax, vehicleList, mainTable, viz);
		vh.setReplan(false);
		vh.setTrafficLightsList(trafficLightsList);
	}
	System.out.println("\n" + "Radius "  + rMax );
	
	
	
	Thread.sleep(3000);


	thread1.start();
	thread2.start();
	thread3.start();
	//thread4.start();
	//thread5.start();

	}
}
