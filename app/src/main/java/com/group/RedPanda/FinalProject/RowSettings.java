package com.group.RedPanda.FinalProject;

public class RowSettings {

	private int imageId;
	private String Settingstitle;
	
	public RowSettings(int image,String title)
	{
		this.imageId=image;
		this.Settingstitle=title;
	}
	
	public int getImageId()
	{
		return imageId;
	}
	
	public void setImageId(int image)
	{
		this.imageId=image;
	}
	
	public String getTitle()
	{
		return Settingstitle;
	}
	
	public void SetTitle(String title)
	{
		Settingstitle=title;
	}
}
