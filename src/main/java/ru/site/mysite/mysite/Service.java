package ru.site.mysite.mysite;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;

public class Service {

    String serviceUrl;
    byte[] img;
    public Service(String _serviceUrl, byte[] _img){
        this.serviceUrl = _serviceUrl;
        this.img = _img;

    }

    public String requestToService(){
        try{
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(this.serviceUrl);

            MultipartEntity multipartEntity = new MultipartEntity();
            File f = createTmpFile(this.img);
            ContentBody cb = new FileBody(f);
            multipartEntity.addPart("fileToUpload", cb);
            post.setEntity(multipartEntity);
            HttpResponse  response = httpClient.execute(post);
            HttpEntity responseEntity = response.getEntity();
            return EntityUtils.toString(responseEntity);
        }
        catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    private File createTmpFile(byte[] img){
        try {
            File f = File.createTempFile( "img", ".png", new File("/tmp"));
            FileOutputStream stream = new FileOutputStream(f);
            stream.write(img);
            return f;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
