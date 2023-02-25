package restapi.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Car {
	public String make;
	public String model;
	public String vin;
	public Metadata metadata;
	public Perdayrent perdayrent;
	public Metrics metrics;

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "\n Make: " + make + " model :" + model + "\n Metadata >> color: " + metadata.Color + " Notes: "
				+ metadata.Notes + "\n PerDayREntal >>  Price:" + perdayrent.Price + " PerDayRental Discount : "
				+ perdayrent.Discount + "\n Metrics >>  yoymaintenancecost " + metrics.yoymaintenancecost
				+ " depreciation: " + metrics.depreciation + " \n Rental Counts  >> lastweek : "
				+ metrics.rentalcount.lastweek + " yeartodate :" + metrics.rentalcount.yeartodate;
	}
}
