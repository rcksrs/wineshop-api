package com.rcksrs.wineshop.core.usecase;

import com.rcksrs.wineshop.core.client.api.CustomerClient;
import com.rcksrs.wineshop.core.client.api.ProductClient;
import com.rcksrs.wineshop.core.exception.CustomerNotFoundException;
import com.rcksrs.wineshop.core.exception.ProductNotFoundException;
import com.rcksrs.wineshop.core.exception.PurchaseNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;

import static com.rcksrs.wineshop.mock.MockResponse.getCustomers;
import static com.rcksrs.wineshop.mock.MockResponse.getProducts;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductRecommendationUseCaseTest {

    @Mock
    private CustomerClient customerClient;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private ProductRecommendationUseCase productRecommendationUseCase;

    @Test
    @DisplayName(value = "Deve recomendar o produto 4 para o usuário 1")
    void shouldRecommendProductForCustomer1() {
        when(productClient.getProducts()).thenReturn(getProducts());
        when(customerClient.getCustomers()).thenReturn(getCustomers());

        var response = productRecommendationUseCase.getRecommendation("document-1");
        assertEquals(4L, response.code());
        assertEquals("type-1", response.type());
    }

    @Test
    @DisplayName(value = "Deve recomendar o produto 5 para o usuário 3")
    void shouldRecommendProductForCustomer3() {
        when(productClient.getProducts()).thenReturn(getProducts());
        when(customerClient.getCustomers()).thenReturn(getCustomers());

        var response = productRecommendationUseCase.getRecommendation("document-3");
        assertEquals(5L, response.code());
        assertEquals("type-2", response.type());
    }

    @Test
    @DisplayName(value = "Deve recomendar o produto 1 para o usuário 8")
    void shouldRecommendProductForCustomer8() {
        when(productClient.getProducts()).thenReturn(getProducts());
        when(customerClient.getCustomers()).thenReturn(getCustomers());

        var response = productRecommendationUseCase.getRecommendation("document-8");
        assertEquals(1L, response.code());
        assertEquals("type-1", response.type());
    }

    @Test
    @DisplayName(value = "Deve recomendar um produto aleatório para o usuário 9")
    void shouldRecommendARandomProductForCustomer9() {
        when(productClient.getProducts()).thenReturn(getProducts());
        when(customerClient.getCustomers()).thenReturn(getCustomers());

        var response = productRecommendationUseCase.getRecommendation("document-9");
        assertNotNull(response);
    }

    @Test
    @DisplayName(value = "Deve lançar exceção ao buscar recomendações para o usuário 5")
    void shouldThrowExceptionForCustomer5() {
        when(productClient.getProducts()).thenReturn(getProducts());
        when(customerClient.getCustomers()).thenReturn(getCustomers());

        assertThrows(PurchaseNotFoundException.class, () -> productRecommendationUseCase.getRecommendation("document-5"));
        verify(productClient).getProducts();
        verify(customerClient).getCustomers();

    }

    @Test
    @DisplayName(value = "Deve lançar exceção ao buscar recomendações para um usuário nao existente")
    void shouldThrowExceptionForCustomerNotFound() {
        when(productClient.getProducts()).thenReturn(getProducts());
        when(customerClient.getCustomers()).thenReturn(getCustomers());

        assertThrows(CustomerNotFoundException.class, () -> productRecommendationUseCase.getRecommendation("document-10"));
        verify(productClient).getProducts();
        verify(customerClient).getCustomers();
    }

    @Test
    @DisplayName(value = "Deve lançar exceção caso não existam produtos")
    void shouldThrowExceptionWhenThereAreNoProducts() {
        when(productClient.getProducts()).thenReturn(Collections.emptyList());
        when(customerClient.getCustomers()).thenReturn(getCustomers());

        assertThrows(ProductNotFoundException.class, () -> productRecommendationUseCase.getRecommendation("document-1"));
        verify(productClient).getProducts();
        verify(customerClient).getCustomers();
    }

}