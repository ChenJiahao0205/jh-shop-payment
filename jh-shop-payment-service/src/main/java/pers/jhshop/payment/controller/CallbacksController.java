package pers.jhshop.payment.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.common.entity.ResultBo;
import pers.jhshop.payment.consts.JhShopPaymentApiConstants;
import pers.jhshop.payment.model.req.CallbacksCreateReq;
import pers.jhshop.payment.model.req.CallbacksQueryReq;
import pers.jhshop.payment.model.req.CallbacksUpdateReq;
import pers.jhshop.payment.model.vo.CallbacksVO;
import pers.jhshop.payment.service.ICallbacksService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
/**
 * <p>
 * 支付回调表 前端控制器
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-03
 */
@Slf4j
@RestController
@RequestMapping(JhShopPaymentApiConstants.API_USER + "callbacks")
@RequiredArgsConstructor
public class CallbacksController {
    private final ICallbacksService callbacksService;

    @GetMapping("test")
    public ResultBo test() {
        return ResultBo.success();
    }

    @PostMapping("create")
    public ResultBo create(@RequestBody CallbacksCreateReq createReq) {
        callbacksService.createBiz(createReq);
        return ResultBo.success();
    }

    @PostMapping("update")
    public ResultBo update(@RequestBody CallbacksUpdateReq updateReq) {
        callbacksService.updateBiz(updateReq);
        return ResultBo.success();
    }

    @GetMapping("getById")
    public ResultBo<CallbacksVO> getById(Long id) {
        CallbacksVO vo = callbacksService.getByIdBiz(id);
        return ResultBo.success(vo);
    }

    @PostMapping("page")
    public ResultBo<Page<CallbacksVO>> page(@RequestBody CallbacksQueryReq queryReq) {
        Page page = callbacksService.pageBiz(queryReq);
        return ResultBo.success(page);
    }
}

