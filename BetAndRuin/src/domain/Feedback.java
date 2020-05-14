package domain;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Feedback  implements Serializable{

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id @GeneratedValue
	private Integer fbNumber;
	private FeedbackType fbtype;
	private String email;
	private String name;
	private String summary;
	private String details;
	private Date submissiondate;

	private String filename;
	private String extension;
	private int filesize;
	private byte[] filedata;
	
	
	public enum FeedbackType{
		SUGGESTION,
		PROBLEM,
		QUESTION;
	}
	
	public Feedback() {
		
	}
	
	public Feedback(FeedbackType fbtype, String email, String name, String summary, String details, File file) {
		this.fbtype = fbtype;
		this.email = email;
		this.name = name;
		this.summary = summary;
		this.details = details;
		this.submissiondate = new Date();
		if(file != null) {
			this.filename = file.toPath().getFileName().toString();
			this.extension = getFileExtension(file);
			this.filesize = (int)file.length();
			
			FileInputStream fis;
			try {	
				filedata = new byte[(int)file.length()];
				fis = new FileInputStream(file);
				fis.read(filedata);
			    fis.close();      
			} catch (FileNotFoundException e) {
				System.err.println("could not resolve path");
				e.printStackTrace();
			}
			catch (IOException e) {
				System.err.println("Error reading file");
				e.printStackTrace();
			}
		}
       
	}
	
	
	private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }


	public Integer getFbNumber() {
		return fbNumber;
	}


	public void setFbNumber(Integer fbNumber) {
		this.fbNumber = fbNumber;
	}


	public FeedbackType getFbtype() {
		return fbtype;
	}


	public void setFbtype(FeedbackType fbtype) {
		this.fbtype = fbtype;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSummary() {
		return summary;
	}


	public void setSummary(String summary) {
		this.summary = summary;
	}


	public String getDetails() {
		return details;
	}


	public void setDetails(String details) {
		this.details = details;
	}


	public Date getSubmissiondate() {
		return submissiondate;
	}


	public void setSubmissiondate(Date submissiondate) {
		this.submissiondate = submissiondate;
	}


	public String getFilename() {
		return filename;
	}


	public void setFilename(String filename) {
		this.filename = filename;
	}


	public String getExtension() {
		return extension;
	}


	public void setExtension(String extension) {
		this.extension = extension;
	}


	public int getFilesize() {
		return filesize;
	}


	public void setFilesize(int filesize) {
		this.filesize = filesize;
	}


	public byte[] getFiledata() {
		return filedata;
	}


	public void setFiledata(byte[] filedata) {
		this.filedata = filedata;
	}


	/*
	public File filebyteToFile(String email) {
		
		TypedQuery<Feedback> query = db.createQuery("SELECT FROM Feedback f WHERE f.email = \"" + email + "\"", Feedback.class);
		List<Feedback> fdbck = query.getResultList();
		
		
		FileOutputStream fos;
		File file = null;
		Feedback fb = fdbck.get(0);
		
		try {
			if(fb.getExtension().equals("txt")) {
				file = new File(fb.getFilename());
				fos = new FileOutputStream(file);
				fos.write(fb.getFiledata());
			}
			else if(fb.getExtension().equals("png") && fb.getExtension().equals("jpg")){
				
						
				
				file = new File("images/" + fb.getFilename());
				InputStream in = new ByteArrayInputStream(fb.getFiledata());
				BufferedImage bImageFromConvert = ImageIO.read(in);

				ImageIO.write(bImageFromConvert, "jpg", file);
				
				 
			}
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		return file;
	}
	*/
		
	
}
