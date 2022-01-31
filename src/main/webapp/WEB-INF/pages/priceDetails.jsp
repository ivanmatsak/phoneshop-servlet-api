<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<jsp:useBean id="product" type="com.es.phoneshop.model.product.Product" scope="request"/>
<tags:master pageTitle="Product List">

  <h1>Price history</h1>
  <p>
    ${product.description}
  </p>
  <table>

      <tr>
        <td>Start date</td>
        <td>Price</td>
      </tr>
      </thead>
          <c:forEach var="dates" items="${product.getPriceHistory()}">
            <tr>
              <td>
                ${dates.date}
              </td>
              <td>
                <fmt:formatNumber value="${date.price}" type="currency" currencySymbol="${date.currency.symbol}"/>
              </td>
            </tr>
          </c:forEach>
  </table>
</tags:master>