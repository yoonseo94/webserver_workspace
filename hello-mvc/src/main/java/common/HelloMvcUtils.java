package common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class HelloMvcUtils {
	
	public static void main(String[] args) {
		System.out.println(encrypt("1234", "admin"));
	}
	
	/**
	 * SHA256 | SHA512 (SHA1 또는 MD5는 사용하지 말것)
	 * 
	 * 
	 * @param password
	 * @param salt 
	 * @return
	 */
	public static String encrypt(String password, String salt) {
		// 1. 암호화 Hashing
		MessageDigest md = null;
		byte[] encrypted = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
			byte[] input = password.getBytes("utf-8");
			byte[] saltBytes = salt.getBytes("utf-8");
			md.update(saltBytes); // salt값으로 MessageDigest객체 갱신 
			encrypted = md.digest(input); // MessageDigest객체에 raw password전달 및 hashing
//			System.out.println(new String(encrypted)); // ��!gs�'"S�@)>�䘽����Ê��U�y_�m�=$邾��$�{�E�V8��iWH��
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		// 2. 인코딩 (단순문자변환)
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(encrypted);
	}

	/**
	 * 
	 * @param cPage 
	 * @param numPerPage 10
	 * @param totalContents 116
	 * @param url /mvc/admin/memberList
	 * @return
	 * 
	 * prev 없음
	 * 
	 * pageNo영역
	 * <span class='cPage'>1</span>
	 * <a href='/mvc/admin/memberList?cPage=2'>2</a>
	 * <a href='/mvc/admin/memberList?cPage=3'>3</a>
	 * <a href='/mvc/admin/memberList?cPage=4'>4</a>
	 * <a href='/mvc/admin/memberList?cPage=5'>5</a>
	 * 
	 * next영역
	 * <a href='/mvc/admin/memberList?cPage=6'>next</a>
	 * 
	 */
	public static String getPagebar(int cPage, int numPerPage, int totalContents, String url) {
		StringBuilder pagebar = new StringBuilder();
		int totalPages = (int) Math.ceil((double) totalContents / numPerPage) ; // 전체페이지수 
		int pagebarSize = 5;
		int pagebarStart = (cPage - 1) / pagebarSize * pagebarSize + 1; // 1, 6, 11
		int pagebarEnd = pagebarStart + pagebarSize - 1; // 5, 10, 15 
		int pageNo = pagebarStart;
		
		url += "?cPage=";
		
		
		// 이전 prev
		if(pageNo == 1) {
			// prev버튼 비활성화
		}
		else {
			// prev버튼 활성화
			pagebar.append("<a href='" + url + (pageNo - 1) + "'>prev</a>");
			pagebar.append("\n");
		}
		
		// 번호
		while(pageNo <= pagebarEnd && pageNo <= totalPages) {
			if(pageNo == cPage) {
				// 현재페이지인 경우
				pagebar.append("<span class='cPage'>" + pageNo + "</span>");
				pagebar.append("\n");
			}
			else {
				// 현재페이지가 아닌 경우(링크필요)
				pagebar.append("<a href='" + url + pageNo + "'>" + pageNo + "</a>");
				pagebar.append("\n");
			}
			pageNo++;
		}
		// 다음 next
		if(pageNo > totalPages) {
			
		}
		else {
			pagebar.append("<a href='" + url + pageNo + "'>next</a>");
			pagebar.append("\n");
		}
		
		return pagebar.toString();
	}

	
	
	
}
