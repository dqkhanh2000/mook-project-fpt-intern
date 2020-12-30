<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.khanhdq9.model.User"  %>
<%@ page import="com.khanhdq9.model.TodoItem"  %>
<%@ page import="java.util.List"  %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Todo app</title>
    <script src="https://kit.fontawesome.com/3156ef87ae.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" 
        integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container-fluid pb-5">
        <div class="rounded col-lg-8 py-3 mx-auto">
            <div class="header border-bottom pb-3 row">
                <a class="text-warning col-lg-6 col-12" href="${pageContext.request.contextPath }/home"><h1>TODO APP</h1></a>
                
                <div class="col-lg-5 col-12 mx-auto pl-5 pb-3 ml-3">
                    <nav>
                        <div class="nav nav-tabs row" id="nav-tab" role="tablist">
                            <a class="nav-link active" id="timeline-tab" data-toggle="tab" href="#timeline" role="tab" aria-controls="home" aria-selected="true">Chưa hoàn thành</a>
                            <a class="nav-link" id="timeline-tab" data-toggle="tab" href="#complete" role="tab" aria-controls="home" aria-selected="true">Đã hoàn thành</a>
                        </div>
                    </nav>
                </div>

                <div class="row tool-bar col-12 row mx-auto">
                    <form class="form-inline col-lg-8 col-12 row ml-2">
                            <input type="text" name="key" class="form-control col-lg-4 col-6" aria-label="Small" aria-describedby="inputGroup-sizing-sm" placeholder="Tìm kiếm" >
                            <input type="submit" class="btn btn-success ml-3 col-lg-2 col-5" value="Tìm kiếm">
                    </form>
                    <div class="col-lg-4 mt-2 col-12 text-center">
                    	<button class="btn btn-primary btn-sm col-5" data-toggle="modal" data-target="#model"><i class="fa fa-plus p-0" aria-hidden="true"></i> Thêm mới</button>
                    	<a class="btn btn-warning btn-sm col-5" href="${pageContext.request.contextPath }/home?logout=1">Đăng xuất</a>
                    </div>
                </div>
            </div>
            <div class="tab-content d-block mt-3">
                <div class="timeline col-12 tab-pane fade show active" id="timeline" role="tabpanel">
                    
					<% 
						List<TodoItem> list = ((List<TodoItem>)request.getAttribute("list"));
						List<TodoItem> listCompleted = ((List<TodoItem>)request.getAttribute("listCompleted"));
						if(list.isEmpty()) {
							out.print("<h2 class=\"text-white col-10 mx-auto text-center\">Danh sách trống</h2>");
						}
						else
							for(TodoItem item: list) {
					%>
			                    <div class="item row pl-3">
			                        <div class="todo-item pb-3 mb-3 col-10">
			                        	<input type="hidden" class="id" value="<%= item.getId() %>"/>
			                            <h4 class="title"><%= item.getTitle() %></h4>
			                            <p class="content"><%= item.getContent() %></p>
			                        </div>
			                        <div class="timeline-tool col-2">
			                            <div class="row">
			                                <a class="col-4" href="${pageContext.request.contextPath}/home?complete=<%= item.getId() %>"><i class="fa fa-check-circle fa-2x check text-success" aria-hidden="true"></i></a>
			                                <a class="col-4" class="btn-edit" data-toggle="modal" data-target="#model"><i class="far fa-edit fa-2x text-warning edit"></i></a>
			                                <a class="col-4" href="${pageContext.request.contextPath}/home?delete=<%= item.getId() %>"><i class="fa fa-trash fa-2x text-danger delete" aria-hidden="true"></i></a>
			                            </div>
			                        </div>
			                    </div>

	                <%
							}					
					%>
					
				</div>
					<div class="complete col-12 tab-pane fade " id="complete" role="tabpanel">
	                    <div class="item-complete col-11 mx-auto border-bottom row">
	                       
	                       <%
	                       		if(listCompleted.isEmpty()) 
	                       			out.print("<h2 class=\"text-white col-10 mx-auto text-center\">Danh sách trống</h2>");
	                       	
	                       		else
	                       			for(TodoItem item  : listCompleted){
	                       %>
	                       
		                        <div class="title col-10">
		                            <h4 class="text-white"><strike><%= item.getTitle() %></strike></h4>
		                        </div>
		                        <div class="tool col-2 ">
		                            <div class="row">
		                                <a href="${pageContext.request.contextPath}/home?undo=<%= item.getId() %>"> <i class="fa fa-undo fa-2x" aria-hidden="true"></i></a>
		                                <a href="${pageContext.request.contextPath}/home?delete=<%= item.getId() %>"> <i class="fa fa-trash fa-2x delete" aria-hidden="true"></i></a>
		                            </div>
		                        </div>
		                    <%
	                       			}
		                    %>
	                       
	                    </div>
	
	                </div>

            </div>
        </div>
    </div>

    
    <!-- Modal -->
    <div class="modal fade" id="model" tabindex="-1" role="dialog" aria-labelledby="modelTitleId" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                    <div class="modal-header">
                            <h5 class="modal-title">Thêm công việc</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                        </div>
                <form method="post">
                    <div class="modal-body">
                        <div class="container-fluid">
                            <div class="form-group">
                            <label for="">Tiêu đề</label>
                            <input type="text" name="title" id="title" class="form-control" placeholder="" aria-describedby="helpId">
							<input type="hidden" id="update" name="update" value="-1"/>
                            <label for="">Nội dung</label>
                            <textarea class="form-control" name="content" id="content" rows="3"></textarea>
                            
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                        <button type="submit" class="btn btn-primary">Thêm</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
   
    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
 
    <script>
    	$(".fa-edit").click((e) => {
    		let parent = $(e.target).parent().parent().parent().parent().find(".todo-item")
    		console.log(parent.find(".title").text())
    		$("#title").val(parent.find(".title").text())
    		$("#content").val(parent.find(".content").text())
    		$("#update").val(parent.find(".id").val())
    	})
    </script>

</body>
</html>