package model;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import resources.DateAdapter;
import resources.TimeAdapter;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "news")
public class News implements Comparable<News> {
	
	@XmlElement(name="id")
	private long id;
	
	@XmlElement(name="owner")
	private long owner;
	
	@XmlElement(name="datestamp")
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dateStamp;
	
	@XmlElement(name="timestamp")
	@XmlJavaTypeAdapter(TimeAdapter.class)
	private Time timeStamp;
	
	@XmlElement(name="title")
	private String title;

	@XmlElement(name="text")
	private String text;
	
	@XmlElement(name="url")
	private String url;
	
	@XmlElement(name="category")
	private String category;
	
	@XmlElement(name="likes")
	private int likes;
	
	@XmlElement(name="hits")
	private int hits;
	
	@XmlElement(name="image")
	private String image;
	
	
	
	public News(){
		Calendar calendar = new GregorianCalendar();
		dateStamp = calendar.getTime();
		timeStamp = new Time(calendar.getTimeInMillis());
		hits = 0;
		likes = 0;
	
	}
	
	public Date getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
	public Time getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(Time timeStamp) {
		this.timeStamp = timeStamp;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOwner() {
		return owner;
	}
	public void setOwner(long owner) {
		this.owner = owner;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	public int compareTo(News n){
		if(this.id<n.getId()){
			return -1;
		}else if(this.id>n.getId()){
			return 1;
		}
		return 0;
	}
	
	

}
