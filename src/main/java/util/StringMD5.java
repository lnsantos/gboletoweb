package util;
import java.math.BigInteger;
import java.security.*;

import entidade.Retorno;

public class StringMD5 {
	
	
	public String sendPassword(String s) {
		System.out.println("OPEN SENDPASSWORD LOADING...");
	       MessageDigest m;
		try {
			m = MessageDigest.getInstance("MD5");
			m.update(s.getBytes(),0,s.length());
			
		    return new BigInteger(1,m.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("CATCH SENDPASSWORD : " + e);
			return "PROBLEMA ";	     
		}
	}
}
