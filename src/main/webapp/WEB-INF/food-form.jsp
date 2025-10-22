<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${food != null ? "Sửa món ăn" : "Thêm món ăn"} - Yummy Bytes</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script>
        function validateForm() {
            const name = document.forms["foodForm"]["name"].value.trim();
            const price = parseFloat(document.forms["foodForm"]["price"].value);

            if (name.length < 7) {
                alert("Tên món ăn phải dài ít nhất 7 ký tự!");
                return false;
            }
            if (isNaN(price) || price <= 0) {
                alert("Giá bán phải lớn hơn 0!");
                return false;
            }
            return true;
        }
    </script>
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="foods">
        <img src="../assets/logo.png" height="40" class="mr-2"> Yummy Bytes
    </a>
</nav>

<div class="container mt-4">
    <h3 class="text-primary mb-3">${food != null ? "Cập nhật món ăn" : "Thêm món ăn mới"}</h3>

    <form name="foodForm" method="post" action="${food != null ? 'update-food' : 'insert-food'}"
          onsubmit="return validateForm()" enctype="multipart/form-data" class="bg-white p-4 rounded shadow-sm">

        <div class="form-group">
            <label for="name">Tên món ăn</label>
            <input type="text" class="form-control" id="name" name="name"
                   value="${food.name}" placeholder="Nhập tên món ăn">
        </div>

        <div class="form-group">
            <label for="categoryCode">Danh mục</label>
            <select class="form-control" id="categoryCode" name="categoryCode">
                <option value="NUONG" ${food.categoryCode == 'NUONG' ? 'selected' : ''}>Món nướng</option>
                <option value="LUOC" ${food.categoryCode == 'LUOC' ? 'selected' : ''}>Món luộc</option>
                <option value="CHAY" ${food.categoryCode == 'CHAY' ? 'selected' : ''}>Món chay</option>
                <option value="DOUONG" ${food.categoryCode == 'DOUONG' ? 'selected' : ''}>Đồ uống</option>
            </select>
        </div>

        <div class="form-group">
            <label for="price">Giá bán (VNĐ)</label>
            <input type="number" step="0.01" class="form-control" id="price" name="price"
                   value="${food.price}" placeholder="Nhập giá bán">
        </div>

        <div class="form-group">
            <label for="description">Mô tả</label>
            <textarea class="form-control" id="description" name="description" rows="3"
                      placeholder="Mô tả món ăn">${food.description}</textarea>
        </div>

        <div class="form-group">
            <label for="imagePath">Ảnh đại diện</label>
            <input type="file" class="form-control-file" id="imagePath" name="imagePath">
            <c:if test="${food.imagePath != null}">
                <img src="${food.imagePath}" alt="Ảnh hiện tại" width="100" class="mt-2 rounded">
            </c:if>
        </div>

        <button type="submit" class="btn btn-success">${food != null ? "Cập nhật" : "Thêm mới"}</button>
        <a href="foods" class="btn btn-secondary">Quay lại</a>
    </form>
</div>

</body>
</html>
