<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> <%-- đổi sang jakarta.tags.core nếu Tomcat 10+ --%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý món ăn - Yummy Bytes</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="bg-light">

<!-- HEADER -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">
        <img src="../assets/logo.png" height="40" class="mr-2"> Yummy Bytes
    </a>
</nav>

<div class="container my-4">
    <div class="d-flex justify-content-between align-items-center mb-3">
        <h3 class="text-primary">Danh sách món ăn đang bán</h3>
        <a href="${pageContext.request.contextPath}/add-food" class="btn btn-success">+ Thêm món ăn</a>
    </div>

    <table class="table table-bordered table-striped table-hover">
        <thead class="thead-dark">
        <tr>
            <th>Mã</th>
            <th>Tên món</th>
            <th>Danh mục</th>
            <th>Giá (VNĐ)</th>
            <th>Mô tả</th>
            <th>Ảnh</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="food" items="${foods}">
            <c:if test="${food.status eq 'DANG_BAN'}">
                <tr>
                    <td>${food.code}</td>
                    <td>${food.name}</td>
                    <td>${food.categoryCode}</td>
                    <td>${food.price}</td>
                    <td>${food.description}</td>
                    <td>
                        <img src="${food.imagePath}" alt="Ảnh món ăn" style="width:60px;height:60px;object-fit:cover;border-radius:6px;">
                    </td>
                    <td><span class="badge badge-success">${food.status}</span></td>
                    <td>
                        <a href="edit-food?id=${food.id}" class="btn btn-sm btn-primary">Sửa</a>
                        <a href="delete-food?id=${food.id}" class="btn btn-sm btn-danger"
                           onclick="return confirm('Bạn có chắc muốn dừng bán món này không?')">Xoá</a>
                    </td>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>
</div>

<footer class="bg-dark text-center text-white py-3">
    <small>© 2025 Yummy Bytes Restaurant</small>
</footer>

</body>
</html>
