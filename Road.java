
public class Road {

  int id;
  int type;
  String name;
  String city;
  int oneWay;
  int speed;
  int roadclass;
  int notforcar;
  int notforpede;
  int notforbicy;

  public Road(int i, int t, String n, String c, int ow, int sp, int rc, int car, int ped, int bic) {
    id = i;
    type = t;
    name = n;
    city = c;
    oneWay = ow;
    speed = sp;
    roadclass = rc;
    notforcar = car;
    notforpede = ped;
    notforbicy = bic;

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public int getOneWay() {
    return oneWay;
  }

  public void setOneWay(int oneWay) {
    this.oneWay = oneWay;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public int getRoadclass() {
    return roadclass;
  }

  public void setRoadclass(int roadclass) {
    this.roadclass = roadclass;
  }

  public int getNotforcar() {
    return notforcar;
  }

  public void setNotforcar(int notforcar) {
    this.notforcar = notforcar;
  }

  public int getNotforpede() {
    return notforpede;
  }

  public void setNotforpede(int notforpede) {
    this.notforpede = notforpede;
  }

  public int getNotforbicy() {
    return notforbicy;
  }

  public void setNotforbicy(int notforbicy) {
    this.notforbicy = notforbicy;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public int getID() {
    return id;
  }

}
