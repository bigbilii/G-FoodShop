package me.guoxin.front.controller;

import me.guoxin.dto.AddressDTO;
import me.guoxin.manager.service.AddressService;
import me.guoxin.pojo.GfsAddress;
import me.guoxin.pojo.Result;
import me.guoxin.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController()
public class AddressController {

    @Resource
    private AddressService addressService;

    @PostMapping(value = "/{id}/address/insert")
    public Result insert(@RequestBody AddressDTO addressDTO, @PathVariable("id") Long id) {
        addressService.insert(addressDTO, id);
        return new ResultUtil<>().setData(null);
    }

    @GetMapping(value = "/{id}/address/select")
    public Result select(@PathVariable("id") Long id) {
        List<GfsAddress> list = addressService.selectByUserId(id);
        return new ResultUtil<>().setData(list);
    }

    @DeleteMapping(value = "/address/{id}/delete")
    public Result delete(@PathVariable("id") Long id) {
        addressService.deleteByUserId(id);
        return new ResultUtil<>().setData(null);
    }
}
