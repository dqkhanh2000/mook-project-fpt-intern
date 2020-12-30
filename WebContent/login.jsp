<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login to Todo app</title>
    <script src="https://kit.fontawesome.com/3156ef87ae.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" 
        integrity="sha384-JcKb8q3iqJ61gNV9KGb8thSsNjpSL0n8PARn9HuZOnIxN0hoP+VmmDGMN5t9UJ0Z" crossorigin="anonymous">
        <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="container-fluid mt-5 d-flex">
        <div class="rounded col-lg-4 pt-3 mx-auto">
            <div class="header d-block border-bottom ">
                <h3 class="text-warning text-center">
                    ĐĂNG NHẬP
                </h3>
            </div>
            <div class="content d-block mt-3">
                <form action="" method="post">
                    <div class="input-group col-lg-10 col-12 mx-auto">
                        <div class="input-group-prepend">
                            <i class="fa fa-user bg-warning" aria-hidden="true"></i>
                        </div>
                        <input type="text" class="form-control col-12" name="username" placeholder="Username">
                    </div>
                    <div class="input-group col-lg-10 col-12 mx-auto mt-3" >
                        <div class="input-group-prepend">
                            <i class="fa fa-key bg-warning" aria-hidden="true"></i>
                        </div>
                        <input type="password" class="form-control col-12" name="password" placeholder="Password">
                    </div>
                    <div class="input-group col-lg-10 col-12 mx-auto mt-3">
                    	<p class="mb-2 col-12 text-center text-danger">${errString}</p>
                        <input class="btn btn-warning text-dark mx-auto d-block" type="submit" value="Đăng nhập">
                        <p class="mx-auto text-center mt-2">Chưa có tài khoản?<a href="${pageContext.request.contextPath}/sign-up" class="alert-link text-warning">Đăng ký</a> ngay.</p>
                    </div>
                </form>
            </div>
        </div>
    </div>    
</body>
</html>