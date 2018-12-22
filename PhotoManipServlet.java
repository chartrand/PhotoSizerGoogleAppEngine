package net.chartrand.doug.photomanip;

import static com.google.appengine.api.urlfetch.FetchOptions.Builder.disallowTruncate;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


















import org.datanucleus.util.Base64;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.appengine.api.images.CompositeTransform;
import com.google.appengine.api.images.Image;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesService.OutputEncoding;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.OutputSettings;
import com.google.appengine.api.images.Transform;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;


class Meta
{
	 String dateTaken = null;
	 int orientation = 1;
	 int height = 0;
	 int width = 0;
	 public String toString()
	 {
		 return "orientation: " + orientation +
				 ", dateTaken: " + dateTaken +
				 ", width: " + width +
				 ", height: " + height;
	 }
}

@SuppressWarnings("serial")
public class PhotoManipServlet extends HttpServlet {
	
	
	/**
 public void doGet(HttpServletRequest req,
 HttpServletResponse resp)
 throws IOException {
     Enumeration<String> parameterNames = req.getParameterNames();
     String url = null;
     int rotate = 0;
     int size=0;
     boolean test=false;
     boolean getDateTaken = false;
     while (parameterNames.hasMoreElements()) {
         String paramName = parameterNames.nextElement();
         if (paramName.equalsIgnoreCase("url"))
         {
	         String[] paramValues = req.getParameterValues(paramName);
	         for (int i = 0; i < paramValues.length; i++) {
	             String paramValue = paramValues[i];
	             url=paramValue;
	         }
         }
         if (paramName.equalsIgnoreCase("rotate"))
         {
	         String[] paramValues = req.getParameterValues(paramName);
	         for (int i = 0; i < paramValues.length; i++) {
	             String paramValue = paramValues[i];
	             rotate=Integer.valueOf(paramValue).intValue();
	         }
         }
         if (paramName.equalsIgnoreCase("size"))
         {
	         String[] paramValues = req.getParameterValues(paramName);
	         for (int i = 0; i < paramValues.length; i++) {
	             String paramValue = paramValues[i];
	             size=Integer.valueOf(paramValue).intValue();
	         }
         }
         if (paramName.equalsIgnoreCase("test"))
         {
	         test = true;
         }
         if (paramName.equalsIgnoreCase("getDateTaken"))
         {
	         getDateTaken = true;
         }
     }
     // url = "http://googledrive.com/host/0B7HZjn1EQgIxcUpEeE5CcGJCdnM/Family/2013/2013-11/CAM00367.jpg";
     if (url != null && (rotate != 0 || size != 0))
     {
    	 URL urlo = new URL(url);
    	 byte[] b = URLFetchServiceFactory.getURLFetchService().fetch(
    			 new HTTPRequest(urlo, HTTPMethod.GET, 
    					 disallowTruncate().setDeadline(30.0))).getContent();
    	 Image oldImage = ImagesServiceFactory.makeImage(b);
    	 Image i = processImage(oldImage, rotate, size);
    	 resp.setContentType("image/jpeg");
    	 resp.setContentLength(i.getImageData().length);
    	 resp.getOutputStream().write(i.getImageData());
    	 resp.getOutputStream().flush();
    	 resp.getOutputStream().close();
     }
     else if (url != null && test)
     {
    	 URL urlo = new URL(url);
    	 byte[] b = URLFetchServiceFactory.getURLFetchService().fetch(
    			 new HTTPRequest(urlo, HTTPMethod.GET, 
    					 disallowTruncate().setDeadline(30.0))).getContent();
    	 Image oldImage = ImagesServiceFactory.makeImage(b);
    	 Meta response = getMeta(oldImage);
    	 resp.setContentType("text/plain");
    	 resp.setContentLength(response.toString().length());
    	 PrintWriter out = resp.getWriter();
    	 out.write(response.toString());
    	 out.flush();
    	 out.close();
     }
     else if (url != null && getDateTaken)
     {
    	 URL urlo = new URL(url);
    	 byte[] b = URLFetchServiceFactory.getURLFetchService().fetch(
    			 new HTTPRequest(urlo, HTTPMethod.GET, 
    					 disallowTruncate().setDeadline(30.0))).getContent();
    	 Image oldImage = ImagesServiceFactory.makeImage(b);
    	 Meta response = getMeta(oldImage);
    	 resp.setContentType("text/plain");
    	 String date = "";
    	 if (response.dateTaken != null) date = response.dateTaken;
    	 resp.setContentLength(date.length());
    	 PrintWriter out = resp.getWriter();
    	 out.write(date.toString());
    	 out.flush();
    	 out.close();
     }
 }
 **/
	/**
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            DriveQuickstart.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println(
                "Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }
	public static Drive getDriveService(String token) throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
        **/


     public void doPost(HttpServletRequest req,
    		 HttpServletResponse resp)
    		 throws IOException {
    	 		 byte[] photo = null;
    		     int rotate = 0;
    		     int size=0;
    		     boolean test=false;
    		     String token = null;
    		     String fileid = null;
    		    		 
    		     boolean getDateTaken = false;
    		     if (req.getParameter("rotate") != null)
    		    	 rotate = Integer.valueOf(req.getParameter("rotate")).intValue();
    		     if (req.getParameter("size") != null)
    		    	 size = Integer.valueOf(req.getParameter("size")).intValue();
    		     if (req.getParameter("test") != null)
    		    	 test = true;
    		     if (req.getParameter("getDateTaken") != null)
    		    	 getDateTaken = true;
//    		     if (req.getParameter("photo") != null)
//    		    	 photo = Base64.decode(req.getParameter("photo"));
    		     if (req.getParameter("token") != null)
    		    	 token = req.getParameter("token");
    		     if (req.getParameter("fileid") != null)
    		    	 fileid = req.getParameter("fileid");
    		     
    		     // url = "http://googledrive.com/host/0B7HZjn1EQgIxcUpEeE5CcGJCdnM/Family/2013/2013-11/CAM00367.jpg";
    		     if (test)
    		     {
    		    	 resp.setContentType("text/plain");
    		    	 String data = "rotate " + rotate + " size " + size + " token " + token + " fileid " + fileid +"\n";
    		    	 URL url = new URL("https://www.googleapis.com/drive/v3/files/"+fileid+"?alt=media");
    		    	 data += url + "\n";
    		    	 HttpURLConnection con = (HttpURLConnection)url.openConnection();
    		    	 con.setRequestProperty("Authorization", "Bearer " + token);
    		    	 con.setRequestMethod("GET");
    		 		 InputStream is = con.getInputStream();
    		 		 ByteArrayOutputStream bis = new ByteArrayOutputStream();
    		 		 byte[] bytebuff = new byte[4096]; 
    		 		 int n;
    		 		 while ((n = is.read(bytebuff)) > 0 ) 
    		 		 {
    		 			bis.write(bytebuff, 0, n);
    		 		 }
    		 		 is.close();
    		 		 bis.close();
    		 		 data += "Response Length " + bis.size() + "\n";
    		 		 data += bis.toString();
    		    	 resp.setContentLength(data.length());
    		    	 PrintWriter out = resp.getWriter();
    		    	 out.write(data.toString());
    		    	 out.flush();
    		    	 out.close();
    		     }
    		     else if (token != null && fileid != null && (rotate != 0 || size != 0))
    		     {
    		    	 //Credential c = new Credential(null);
    		    	 //Drive drive = new Drive.Builder(GoogleNetHttpTransport.newTrustedTransport(),
    		    		//	 JacksonFactory.getDefaultInstance(),
    		    		//	 null).build();
    		    	 // Drive.
    		    	 URL url = new URL("https://www.googleapis.com/drive/v3/files/"+fileid+"?alt=media");
    		    	 HttpURLConnection con = (HttpURLConnection)url.openConnection();
    		    	 con.setRequestProperty("Authorization", "Bearer " + token);
    		    	 con.setRequestMethod("GET");
    		 		 InputStream is = con.getInputStream();
    		 		 ByteArrayOutputStream bis = new ByteArrayOutputStream();
    		 		 byte[] bytebuff = new byte[4096]; 
    		 		 int n;
    		 		 while ((n = is.read(bytebuff)) > 0 ) 
    		 		 {
    		 			bis.write(bytebuff, 0, n);
    		 		 }
    		 		 is.close();
    		 		 bis.close();

    		 		Image oldImage = ImagesServiceFactory.makeImage(bis.toByteArray());
    		    	 Image i = processImage(oldImage, rotate, size);
    		    	 resp.setContentType("image/jpeg");
    		    	 resp.setContentLength(i.getImageData().length);
    		    	 resp.getOutputStream().write(i.getImageData());
    		    	 resp.getOutputStream().flush();
    		    	 resp.getOutputStream().close();
    		     }
    		     else if (photo != null && test)
    		     {
    		    	 Image oldImage = ImagesServiceFactory.makeImage(photo);
    		    	 Meta response = getMeta(oldImage);
    		    	 resp.setContentType("text/plain");
    		    	 resp.setContentLength(response.toString().length());
    		    	 PrintWriter out = resp.getWriter();
    		    	 out.write(response.toString());
    		    	 out.flush();
    		    	 out.close();
    		     }
    		     else if (photo != null && getDateTaken)
    		     {
    		    	 Image oldImage = ImagesServiceFactory.makeImage(photo);
    		    	 Meta response = getMeta(oldImage);
    		    	 resp.setContentType("text/plain");
    		    	 String date = "";
    		    	 if (response.dateTaken != null) date = response.dateTaken;
    		    	 resp.setContentLength(date.length());
    		    	 PrintWriter out = resp.getWriter();
    		    	 out.write(date.toString());
    		    	 out.flush();
    		    	 out.close();
    		     }
 }
 
 
 static Meta getMeta(Image o)
 {
	 Meta returnMeta = new Meta();
	 byte[] bytes = o.getImageData();
	 getEXIFMeta(bytes, returnMeta);
	 returnMeta.height = o.getHeight();
	 returnMeta.width = o.getWidth();
	 return returnMeta;
 }
 
 private static void getEXIFMeta(byte[] bytes, Meta meta) {
	  ByteArrayInputStream bis = null;
	  try {
		  bis = new ByteArrayInputStream(bytes);
		  Metadata metadata = ImageMetadataReader.readMetadata(new BufferedInputStream(bis));
		  ExifIFD0Directory dir = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
		  if (dir != null && dir.containsTag(274)) {
				meta.orientation = dir.getInt(274); // 274 is the EXIF orientation standard code
		  }
		  if (dir != null && dir.containsTag(306)) {
				meta.dateTaken = dir.getString(306); // 306 is the EXIF date/time standard code
				// Parse the data and return as YYYYMMDDHHMMSS
				meta.dateTaken = meta.dateTaken.replaceAll(" ", "");
				meta.dateTaken = meta.dateTaken.replaceAll(":", "");
		        String yeartest = meta.dateTaken.substring(0, 4);
		        if (Integer.valueOf(yeartest).intValue() < 2000) meta.dateTaken = null;
		  }
	  } catch (Exception e) {
		  // return e.toString();
	  } finally {
		  if (bis != null) try {
			  bis.close();
		  } catch (IOException e) {
			  // nothing
		  }
	  }
 }
 
 static Image processImage(Image o, int degrees, int size)
 {
	 Meta meta = null;
	 if (degrees == -1)
	 {
		 if (meta == null) meta = getMeta(o);
		 if (meta.orientation == 3) degrees = 180;
		 else if (meta.orientation == 5 || meta.orientation == 6) degrees = 90;
		 else if (meta.orientation == 7 || meta.orientation == 8) degrees = 270;
		 else degrees = 0;
	 }
	 if (size != 0)
	 {
		 if (meta == null) meta = getMeta(o);
		 if (meta.width > size || meta.height > size) {}
		 else {size = 0;}
	 }
	 ImagesService imagesService = ImagesServiceFactory.getImagesService();
	 CompositeTransform composite = ImagesServiceFactory.makeCompositeTransform();
	 if (degrees != 0)
	 {
		 Transform rotate = ImagesServiceFactory.makeRotate(degrees);
		 composite.concatenate(rotate);
	 }
	 if (size != 0)
	 {
		 Transform resize = ImagesServiceFactory.makeResize(size, size);
		 composite.concatenate(resize);
	 }
	 OutputSettings enc = new OutputSettings(OutputEncoding.JPEG);
	 enc.setQuality(90);
	 
     Image newImage;
     if (degrees != 0 || size != 0)
     {
    	 newImage = imagesService.applyTransform(composite, o, enc); 
     }
     else
     {
    	 if (meta == null) meta = getMeta(o);
    	 Transform temp = ImagesServiceFactory.makeResize(meta.width, meta.height);
    	 newImage = imagesService.applyTransform(temp, o, enc);
     }
	 return newImage;
 }
}