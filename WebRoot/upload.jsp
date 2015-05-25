<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<script type="text/javascript" src="js/jquery/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery/ajaxfileupload.js"></script>
<script type="text/javascript" src="js/common/upload.js"></script>

	<img id="loading<%= request.getParameter("uploadId") %>" src="images/loading.gif" style="display:none;">
	<input id='fileToUpload<%= request.getParameter("uploadId") %>' type="file" size="45" name='fileToUpload<%= request.getParameter("uploadId") %>' class="input">
	<button class="button" id="buttonUpload" onclick="return ajaxFileUpload('<%= request.getParameter("uploadId") %>');">上传</button>
	<input type="hidden" name='spanId<%= request.getParameter("uploadId") %>' id='spanId<%= request.getParameter("uploadId") %>' value='<%= request.getParameter("spanId") %>'/>
	<input type="hidden" name='textId<%= request.getParameter("uploadId") %>' id='textId<%= request.getParameter("uploadId") %>' value='<%= request.getParameter("textId") %>'/>
	<input type="hidden" name='showType<%= request.getParameter("uploadId") %>' id='showType<%= request.getParameter("uploadId") %>' value='<%= request.getParameter("showType") %>'/>
	<input type="hidden" name='uploadType<%= request.getParameter("uploadId") %>' id='uploadType<%= request.getParameter("uploadId") %>' value='<%= request.getParameter("uploadType") %>'/>
	<input type="hidden" name='imgPath<%= request.getParameter("uploadId") %>' id='imgPath<%= request.getParameter("uploadId") %>' value='<%= request.getParameter("imgPath") %>'/>
	<input type="hidden" name='srcPath<%= request.getParameter("uploadId") %>' id='srcPath<%= request.getParameter("uploadId") %>' value='<%= request.getParameter("srcPath") %>'/>

	