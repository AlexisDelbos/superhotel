



export class Recommandation{

  name : string;
  cityName :string;
  occupancyRate:number;
  recommendation : string;

  constructor(
    name : string,
    cityName:string,
    occupancyRate:number,
    recommendation:string
  ) {
    this.name = name;
    this.cityName = cityName;
    this.occupancyRate = occupancyRate;
    this.recommendation = recommendation

  }

}
