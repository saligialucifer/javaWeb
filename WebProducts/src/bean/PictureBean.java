package bean;

public class PictureBean
{
	private int pictureId;   //Í¼Æ¬id
	private String pictureDpi;  //Í¼Æ¬µÄ·Ö±æÂÊid
	private int clicks;
	private String path;
	private String upload_name;
	private String upload_time;
	private String describe;
	
	public int getPictureId()
	{
		return this.pictureId;
	}
	public void setPictureId(int pictureId)
	{
		this.pictureId = pictureId;
	}
	public String getPictureDpi()
	{
		return this.pictureDpi;
	}
	public void setPictureDpi(String pictureDpi)
	{
		this.pictureDpi = pictureDpi;
	}
	public int getClicks()
	{
		return this.clicks;
	}
	public void setClicks(int clicks)
	{
		this.clicks = clicks;
	}
	public String getPath()
	{
		return this.path;
	}
	public void setPath(String path)
	{
		this.path = path;
	}
	
	public String getUploadName()
	{
		return this.upload_name;
	}
	public void setUploadName(String upload_name)
	{
		this.upload_name = upload_name;
	}
	public String getUploadTime()
	{
		return this.upload_time;
	}
	public void setUploadTime(String upload_time)
	{
		this.upload_time = upload_time;
	}
	public String getDescribe()
	{
		return this.describe;
	}
	public void setDescribe(String describe)
	{
		this.describe = describe;
	}
}
