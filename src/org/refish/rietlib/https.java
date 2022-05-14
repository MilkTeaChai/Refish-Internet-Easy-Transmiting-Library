package org.refish.rietlib;

public class https {
     public https(Url reqURL,String msg){
        HttpsURLConnection httpsConn = (HttpsURLConnection)reqURL.openConnection();
        httpsConn.setDoOutput(true);
     OutputStreamWriter out = new OutputStreamWriter(huc.getOutputStream(), "8859_1");
     out.write( "msg" );
     out.flush();
     out.close();
     InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream();
     }

}
