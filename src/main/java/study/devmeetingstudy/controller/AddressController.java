package study.devmeetingstudy.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.devmeetingstudy.common.exception.global.response.ApiResponseDto;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.dto.address.AddressRequestDto;
import study.devmeetingstudy.dto.address.AddressResponseDto;
import study.devmeetingstudy.service.AddressService;

import java.net.URI;

@Api(tags = {"4. Address"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @ApiOperation(value = "주소 저장", notes = "주소 리소스를 생성합니다.")
    public ResponseEntity<ApiResponseDto<AddressResponseDto>> saveAddress(@RequestBody AddressRequestDto addressRequestDto){
        Address address = addressService.store(addressRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).location(URI.create("/api/addresses" + address.getId()))
                .body(
                        new ApiResponseDto<>("생성됨", HttpStatus.CREATED.value(), AddressResponseDto.from(address))
                );
    }

    // 혹시 모르니 만듬
    @GetMapping("/{addressId}")
    @ApiOperation(value = "주소 조회", notes = "addressId에 해당하는 주소를 조회합니다.")
    public ResponseEntity<ApiResponseDto<AddressResponseDto>> getAddress(@PathVariable Long addressId){
        Address foundAddress = addressService.findAddress(addressId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ApiResponseDto<>("성공", HttpStatus.OK.value(), AddressResponseDto.from(foundAddress))
                );
    }
}
