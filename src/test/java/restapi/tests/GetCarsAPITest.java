package restapi.tests;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import restapi.data.Car;
import restapi.testbase.TestBase;

public class GetCarsAPITest extends TestBase {
	Response res;

	@Test(priority = 0)
	public void getCarsAPITest() {

		System.out.println("getCarsAPITest");
		res = RestAssured.given().get();
		System.out.println(res.getHeaders());
		Assert.assertNotNull(res);
	}

	@Test
	public void printCars() {
		// print all the blue teslas
		System.out.println("******Print all blue Tesla************");
		List<Car> cars = Arrays.asList(res.getBody().as(Car[].class));
		List<Car> op = cars.stream()
				.filter(a -> a.metadata.Color.equalsIgnoreCase("Blue") && a.make.equalsIgnoreCase("Tesla"))
				.collect(Collectors.toList());
		op.forEach(o -> System.out.println("Make: " + o.make + " Notes: " + o.metadata.Notes));

	}

	public Car getLowestRentalCars() {
		System.out.println("***** get Lowest Rental Cars()**************");

		List<Car> lowestRentCars = Arrays.asList(res.getBody().as(Car[].class));
		Car lowestRentalCarBasedOnPrice = lowestRentCars.stream()
				.collect(Collectors.minBy((x, y) -> x.getPerdayrent().getPrice() - y.getPerdayrent().getPrice())).get();
		System.out.println(
				"*******Lowest Rental Car Based On Price *********\n " + lowestRentalCarBasedOnPrice.toString());

		Car lowestRentalCarBasedOnDiscount = lowestRentCars.stream()
				.collect(
						Collectors
								.minBy((x,
										y) -> ((x.getPerdayrent().getPrice() - x.getPerdayrent().getDiscount())
												- (y.getPerdayrent().getPrice() - y.getPerdayrent().getDiscount()))))
				.get();
		System.out.println(
				"*********Lowest Rental Car Based On Discount ********\n" + lowestRentalCarBasedOnDiscount.toString());
		return lowestRentalCarBasedOnDiscount;

	}

	public Car highestRevenueCar() {
		System.out.println("******Print highest Revenue Car************");

		List<Car> allCars = Arrays.asList(res.getBody().as(Car[].class));
		HashMap<Car, Double> carWithProfits = new HashMap<Car, Double>();

		for (Car car : allCars) {

			double totalExpense = car.metrics.depreciation + car.metrics.yoymaintenancecost;
			double totalRevenue = (car.perdayrent.Price - car.perdayrent.Discount) * car.metrics.rentalcount.yeartodate;
			double totalProfit = totalRevenue - totalExpense;
			carWithProfits.put(car, totalProfit);
		}
		double max = 0.0;
		Car mostProfitCar = null;
		for (Map.Entry<Car, Double> entry : carWithProfits.entrySet()) {
			Car key = entry.getKey();
			double profit = entry.getValue();
			System.out.println("Car :  " + key.make + " profit :  " + profit);

			if (profit > max) {
				max = profit;
				mostProfitCar = key;
//				System.out.println("mostProfit Car :  " + key.make + " profit :  " + profit);
				max = profit;
			}
		}
		System.out.println("Most profited car : " + mostProfitCar);
		return mostProfitCar;
	}

	@Test
	public void validateStatusCode() {

		Assert.assertEquals(res.getStatusCode(), 200);
	}

	@Test
	public void validateContentType() {
		System.out.println("Contect Type :" + res.getContentType());
		Assert.assertTrue(res.getContentType().contains("application/json"));
	}

	@Test
	public void validateContentEncoding() {
		Assert.assertEquals(res.getHeader("Content-Encoding"), "gzip");
	}
}
