package com.lsm1998.puzzle;

public class MainApp{
    public static final String PATH;

    static
    {
        PATH =  MainApp.class.getResource("/img").getPath();
    }

	public static void main(String[] args) {
		PictureMainFrame pf=new PictureMainFrame();
		pf.setVisible(true);
	}
}