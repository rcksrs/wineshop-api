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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListPurchaseUseCaseTest {

    @Mock
    private CustomerClient customerClient;

    @Mock
    private ProductClient productClient;

    @InjectMocks
    private ListPurchaseUseCase listPurchaseUseCase;

    @Test
    @DisplayName(value = "Deve listar todas as compras ordenadas por ordem crescente do valor total")
    void shouldListAllPurchases() {
        when(productClient.getProducts()).thenReturn(getProducts());
        when(customerClient.getCustomers()).thenReturn(getCustomers());

        var response = listPurchaseUseCase.listAll();

        assertEquals(9, response.size());

        assertEquals(2, response.get(0).purchases().size());
        assertEquals(2, response.get(1).purchases().size());
        assertEquals(1, response.get(2).purchases().size());
        assertEquals(1, response.get(3).purchases().size());

        assertEquals("customer-1", response.get(0).name());
        assertEquals("customer-2", response.get(1).name());
        assertEquals("customer-3", response.get(2).name());
        assertEquals("customer-4", response.get(3).name());

        assertEquals(10.0, response.get(0).purchases().get(0).total());
        assertEquals(40.0, response.get(1).purchases().get(0).total());
        assertEquals(300.0, response.get(2).purchases().get(0).total());
        assertEquals(1600.0, response.get(3).purchases().get(0).total());
    }

}