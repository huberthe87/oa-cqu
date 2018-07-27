package jiekou;

public class shebeitaizhang {
	  private String data;
	  public String getData() {return data;}
	  public void setData(String data) {this.data = data;}
	  private static final shebeitaizhang holder = new shebeitaizhang();
	  public static shebeitaizhang getInstance() {return holder;}
	  
	}
