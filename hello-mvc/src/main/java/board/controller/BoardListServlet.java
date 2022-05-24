package board.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.dto.Attachment;
import board.model.dto.BoardExt;
import board.model.service.BoardService;
import common.HelloMvcUtils;


/**
 * 페이징
 * 1. content 영역
 *  - cPage 현재페이지
 *  - numPerPage 한 페이지당 표시할 컨텐츠 수
 *  - 페이징 쿼리
 *  	- start
 *  	- end
 * 2. pagebar 영역
 *  - totalContents 전체컨텐츠수
 *  - totalPages 전체페이지수 
 *  - pagebarSize 페이지바 길이 10
 *  - pageNo 페이지 증감변수
 *  - pageStart ~ pageEnd 페이지바 범위
 *  - url 다음 요청시 url
 */
@WebServlet("/board/boardList")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	
	/**
	 * DQL
	 * select * from board order by reg_date desc;
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 1. 사용자 입력값 처리
			int numPerPage = 10;
			int cPage = 1;
			try {
				cPage = Integer.parseInt(request.getParameter("cPage"));
			} catch(NumberFormatException e) {// 예외발생시 현재페이지는 기본값으로 지정한 1로 처리
				
			}
			Map<String, Object> param = new HashMap<>();
			// 1페이지 (표시될 컨텐츠 수 1 ~ 10)
			// 2페이지 (표시될 컨텐츠 수 11 ~ 20)
			// (1 - 1) * 10 + 1 = 1
			// (2 - 1) * 10 + 1 = 11
			int start = (cPage - 1) * numPerPage + 1;
			int end = cPage * numPerPage;
			param.put("start", start);
			param.put("end", end);
			
			// 2. 업무로직
			// 2.a content영역
			List<BoardExt> list = boardService.findAll(param);
			System.out.println(list);
			// 2.b pagebar영역
			int totalContents = boardService.getTotalContents();
			String pagebar = HelloMvcUtils.getPagebar(cPage, numPerPage, totalContents, request.getRequestURI());
			System.out.println(pagebar);
			
			// 3. view단 처리
			request.setAttribute("list", list);
			request.setAttribute("pagebar", pagebar);
			request.getRequestDispatcher("/WEB-INF/views/board/boardList.jsp")
				.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}	
	}
}


