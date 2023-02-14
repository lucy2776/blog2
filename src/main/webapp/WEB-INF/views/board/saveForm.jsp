<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
			<label for="title">Title</label> 
			<input type="text" class="form-control" placeholder="Enter title" id="title" name="title">
		</div>
		<div class="form-group">
			<label for="content">Content:</label>
			<textarea class="form-control summernote" rows="5" id="content"></textarea>
		</div>
	</form>

		<button id="btn-save" class="btn btn-primary">글 작성</button>
</div>

<script src="/js/board.js"></script>
<script>
      $('.summernote').summernote({
        placeholder: 'Write content here !',
        tabsize: 2,
        height: 300
      });
</script>

<%@include file="../layout/footer.jsp"%>