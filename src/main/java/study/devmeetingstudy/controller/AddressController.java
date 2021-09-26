package study.devmeetingstudy.controller;


import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import study.devmeetingstudy.common.exception.global.error.ErrorResponse;
import study.devmeetingstudy.common.exception.global.response.ApiResDto;
import study.devmeetingstudy.domain.Address;
import study.devmeetingstudy.dto.address.AddressReqDto;
import study.devmeetingstudy.dto.address.AddressResDto;
import study.devmeetingstudy.service.AddressService;

import java.net.URI;

@Api(tags = {"4. Address"})
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/addresses")
@Slf4j
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @ApiOperation(value = "주소 저장", notes = "주소 리소스를 생성합니다.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성됨"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ApiResDto<AddressResDto>> saveAddress(@RequestBody AddressReqDto addressReqDto){
        Address address = addressService.saveAddress(addressReqDto);
        return ResponseEntity.created(URI.create("/api/addresses" + address.getId()))
                .body(
                        ApiResDto.<AddressResDto>builder()
                                .message("생성됨")
                                .status(HttpStatus.CREATED.value())
                                .data(AddressResDto.from(address))
                                .build()
                );
    }

    // 혹시 모르니 만듬
    @GetMapping("/{id}")
    @ApiOperation(value = "주소 조회", notes = "id에 해당하는 주소를 조회합니다.")
    @ApiImplicitParam(name = "id", value = "주소 아이디")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 400, message = "잘못된 요청", response = ErrorResponse.class)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ApiResDto<AddressResDto>> getAddress(@PathVariable Long id){
        Address foundAddress = addressService.findAddress(id);
        return ResponseEntity.ok(
                ApiResDto.<AddressResDto>builder()
                        .message("성공")
                        .status(HttpStatus.OK.value())
                        .data(AddressResDto.from(foundAddress))
                        .build()
        );
    }
}
