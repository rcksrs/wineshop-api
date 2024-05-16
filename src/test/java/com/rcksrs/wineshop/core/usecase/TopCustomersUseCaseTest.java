package com.rcksrs.wineshop.core.usecase;

import com.rcksrs.wineshop.core.client.api.CustomerClient;
import com.rcksrs.wineshop.core.client.api.ProductClient;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.rcksrs.wineshop.mock.MockResponse.getCustomers;
import static com.rcksrs.wineshop.mock.MockResponse.getProducts;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TopCustomersUseCaseTest {

    @Mock
    private CustomerClient customerClient;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private TopCustomersUseCase topCustomersUseCase;

    @Test
    @DisplayName(value = "Deve retornar a lista dos 3 clientes com mais compras e que mais gastaram")
    void shouldReturnTopCustomers() {
        when(productClient.getProducts()).thenReturn(getProducts());
        when(customerClient.getCustomers()).thenReturn(getCustomers());

        var response = topCustomersUseCase.getTopCustomers(3);
        assertEquals(3, response.size());

        assertEquals("customer-9", response.get(0).name());
        assertEquals("customer-2", response.get(1).name());
        assertEquals("customer-8", response.get(2).name());

        assertEquals(5, response.get(0).purchases());
        assertEquals(2, response.get(1).purchases());
        assertEquals(2, response.get(2).purchases());

        assertEquals(150.0, response.get(0).total());
        assertEquals(100.0, response.get(1).total());
        assertEquals(80.0, response.get(2).total());
    }

}