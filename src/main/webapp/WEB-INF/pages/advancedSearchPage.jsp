<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<jsp:useBean id="products" class="java.util.ArrayList" scope="request"/>

<tags:master pageTitle="Advanced search page">
  <h1>Advanced search page</h1>
  <table>
    <tr>
        <td>
            Product code
            <input name="productCode" type="text" value="${not empty error? param.productCode: 'sgs'}">
        </td>
    </tr>
    <tr>
        <td>
            Max price
            <input name="maxPrice" value="${not empty error? param.maxPrice: 700}">
        </td>
    </tr>
    <tr>
        <td>
            Min price
            <input name="minPrice" value="${not empty error? param.minPrice: 10}">
        </td>
    </tr>
    <tr>
        <td>
            Min stock
            <input name="minStock" value="${not empty error? param.minStock: 1}">
        </td>
    </tr>
  </table>
  <a>
  <form action="${pageContext.servletContext.contextPath}/advancedSearchPage" method = "POST">
    <button>Search</button>
  </form>
  <form method="post" action = "${pageContext.servletContext.contextPath}/advancedSearchPage">
    <c:forEach var="product" items="${products}">
      <tr>
                  <td>
                    <img class="product-tile" src="${product.imageUrl}">
                  </td>
                  <td>
                      <a href="${pageContext.servletContext.contextPath}/products/${product.id}">
                          ${product.description}
                      </a>
                  </td>
                  <td>
                      <input name="quantity" value="${not empty error? param.quantity: 1}" class= "quantity">
                      <input type="hidden" name="productId" value="${product.id}"/>
                      <c:if test="${not empty error}">
                          <div class="error">
                              ${errors[product.id]}
                          </div>
                      </c:if>
                  </td>
                  <td class="price">
                      <a href="${pageContext.servletContext.contextPath}/products/price/${product.id}">
                          <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="${product.currency.symbol}"/>
                      </a>
                  </td>
                </tr>
    </c:forEach>
    </form>
  </a>
</tags:master>