package study.devmeetingstudy.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.dto.address.AddressReqDto;
import study.devmeetingstudy.repository.AddressRepository;
import study.devmeetingstudy.service.study.AddressService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @InjectMocks
    private AddressService addressService;

    @Mock
    private AddressRepository addressRepository;

    @DisplayName("주소 저장")
    @Test
    void saveAddress() {
        //given
        AddressReqDto addressReqDto = new AddressReqDto("경기도", "광주시", "오포읍");
        Address expectedAddress = Address.create(addressReqDto);
        doReturn(expectedAddress).when(addressRepository).save(any(Address.class));

        //when

        Address saveAddress = addressService.saveAddress(addressReqDto);

        //then
        assertEquals(expectedAddress, saveAddress);
    }


    @DisplayName("주소 조회")
    @Test
    void findAddress() {
        //given
        Long id = 1L;
        Address expectedAddress = createAddress();
        doReturn(Optional.of(expectedAddress)).when(addressRepository).findById(anyLong());
        //when
        Address address = addressService.findAddressById(id);

        //then
        assertEquals(expectedAddress, address);
    }

    private Address createAddress(){
        return Address.builder()
                .id(1L)
                .address1("경기도")
                .address2("광주시")
                .address3("오포읍")
                .build();
    }
}