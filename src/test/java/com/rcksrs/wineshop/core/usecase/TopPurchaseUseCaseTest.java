package com.rcksrs.wineshop.core.usecase;

import com.rcksrs.wineshop.core.client.api.CustomerClient;
import com.rcksrs.wineshop.core.client.api.ProductClient;
import com.rcksrs.wineshop.core.exception.PurchaseNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.rcksrs.wineshop.mock.MockResponse.getCustomers;
import static com.rcksrs.wineshop.mock.MockResponse.getProducts;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopPurchaseUseCaseTest {

    @Mock
    private CustomerClient customerClient;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private TopPurchaseUseCase topPurchaseUseCase;

    @Test
    @DisplayName(value = "Deve retornar a maior compra do ano de 2010")
    void shouldReturnTopPurchaseForYear2010() {
        when(productClient.getProducts()).thenReturn(getProducts());
        when(customerClient.getCustomers()).thenReturn(getCustomers());

        var response = topPurchaseUseCase.getTopPurchaseForYear(2010);

        assertEquals("customer-4", response.name());
        assertEquals("document-4", response.document());
        assertEquals(4L, response.purchase().code());
        assertEquals(1600.0, response.purchase().total());
    }

    @Test
    @DisplayName(value = "Deve retornar a maior compra do ano de 2020")
    void shouldReturnTopPurchaseForYear2020() {
        when(productClient.getProducts()).thenReturn(getProducts());
        when(customerClient.getCustomers()).thenReturn(getCustomers());

        var response = topPurchaseUseCase.getTopPurchaseForYear(2020);

        assertEquals("customer-2", response.name());
        assertEquals("document-2", response.document());
        assertEquals(2L, response.purchase().code());
        assertEquals(60.0, response.purchase().total());
    }

    @Test
    @DisplayName(value = "Deve lançar uma exceção caso não exista nenhuma compra para o ano informado")
    void shouldThrowExceptionForYear2000() {
        when(productClient.getProducts()).thenReturn(getProducts());
        when(customerClient.getCustomers()).thenReturn(getCustomers());

        assertThrows(PurchaseNotFoundException.class, () -> topPurchaseUseCase.getTopPurchaseForYear(2000));
        verify(productClient).getProducts();
        verify(customerClient).getCustomers();
    }

}