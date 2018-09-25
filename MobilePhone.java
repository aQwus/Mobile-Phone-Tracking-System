class MobilePhone{
  int id;
  Boolean status; //true->On false->Off
  Exchange location;

  MobilePhone(int number){
    this.id = number;
    this.status = false;
    this.location = null;
  }

  MobilePhone(int number, Boolean status, Exchange location){
    this.id = number;
    this.status = status;
    this.location = location;
  }

  public int number(){
    return id;
  }

  public Boolean status(){
    return status;
  }

  public void switchOn(){
    status = true;
  }

  public void switchOff(){
    status = false;
  }

  public Exchange location(){
    return location;
  }
}

class MobilePhoneSet{
  Myset myset = new Myset();
}
