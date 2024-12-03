package pers.jhshop.payment.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import pers.jhshop.payment.model.entity.Payments;
import pers.jhshop.payment.model.req.PaymentsCreateReq;
import pers.jhshop.payment.model.req.PaymentsQueryReq;
import pers.jhshop.payment.model.req.PaymentsUpdateReq;
import pers.jhshop.payment.model.vo.PaymentsVO;
import java.util.Map;
import java.util.List;

/**
 * <p>
 * 支付表 服务类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-03
 */
public interface IPaymentsService extends IService<Payments> {

    void createBiz(PaymentsCreateReq createReq);

    void updateBiz(PaymentsUpdateReq updateReq);

    PaymentsVO getByIdBiz(Long id);

    Page<PaymentsVO> pageBiz(PaymentsQueryReq queryReq);

    Page<Payments> page(PaymentsQueryReq queryReq);

    List<Payments> listByQueryReq(PaymentsQueryReq queryReq);

    Map<Long, Payments> getIdEntityMap(List<Long> ids);

    Payments getOneByQueryReq(PaymentsQueryReq queryReq);

}
