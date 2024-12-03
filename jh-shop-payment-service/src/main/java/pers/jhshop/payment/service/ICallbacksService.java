package pers.jhshop.payment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.jhshop.payment.model.entity.Callbacks;
import pers.jhshop.payment.model.req.CallbacksCreateReq;
import pers.jhshop.payment.model.req.CallbacksQueryReq;
import pers.jhshop.payment.model.req.CallbacksUpdateReq;
import pers.jhshop.payment.model.vo.CallbacksVO;
import java.util.Map;
import java.util.List;

/**
 * <p>
 * 支付回调表 服务类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-03
 */
public interface ICallbacksService extends IService<Callbacks> {

    void createBiz(CallbacksCreateReq createReq);

    void updateBiz(CallbacksUpdateReq updateReq);

    CallbacksVO getByIdBiz(Long id);

    Page<CallbacksVO> pageBiz(CallbacksQueryReq queryReq);

    Page<Callbacks> page(CallbacksQueryReq queryReq);

    List<Callbacks> listByQueryReq(CallbacksQueryReq queryReq);

    Map<Long, Callbacks> getIdEntityMap(List<Long> ids);

    Callbacks getOneByQueryReq(CallbacksQueryReq queryReq);

}
