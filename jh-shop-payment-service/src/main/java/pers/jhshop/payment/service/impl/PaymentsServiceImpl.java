package pers.jhshop.payment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.payment.model.req.PaymentsCreateReq;
import pers.jhshop.payment.model.req.PaymentsQueryReq;
import pers.jhshop.payment.model.req.PaymentsUpdateReq;
import pers.jhshop.payment.model.vo.PaymentsVO;
import pers.jhshop.payment.model.entity.Payments;
import pers.jhshop.payment.mapper.PaymentsMapper;
import pers.jhshop.payment.service.IPaymentsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.jhshop.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.apache.commons.lang3.StringUtils;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Function;

/**
 * <p>
 * 支付表 服务实现类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentsServiceImpl extends ServiceImpl<PaymentsMapper, Payments> implements IPaymentsService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBiz(PaymentsCreateReq createReq) {


        Payments entity = new Payments();
        BeanUtil.copyProperties(createReq, entity);

        boolean insertResult = entity.insert();

        if (!insertResult) {
            throw new ServiceException("数据插入失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBiz(PaymentsUpdateReq updateReq) {

        // 1.入参有效性判断
        if (Objects.isNull(updateReq.getId())) {
            throw new ServiceException("id不能为空");
        }

        Payments entity = getById(updateReq.getId());
        if (Objects.isNull(entity)) {
            throw new ServiceException("支付表不存在");
        }

        // 2.重复性判断
        Payments entityToUpdate = new Payments();
        BeanUtil.copyProperties(updateReq, entityToUpdate);

        boolean updateResult = entityToUpdate.updateById();
        if (!updateResult) {
            throw new ServiceException("数据更新失败");
        }
    }

    @Override
    public PaymentsVO getByIdBiz(Long id) {
        // 1.入参有效性判断
        if (Objects.isNull(id)) {
            throw new ServiceException("id不能为空");
        }

        // 2.存在性判断
        Payments entity = getById(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException("支付表不存在");
        }

        PaymentsVO vo = new PaymentsVO();
        BeanUtil.copyProperties(entity, vo);

            return vo;
    }

    @Override
    public Page<PaymentsVO> pageBiz(PaymentsQueryReq queryReq) {
        Page<Payments> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        page.addOrder(OrderItem.desc("id"));

        LambdaQueryWrapper<Payments> queryWrapper = getLambdaQueryWrapper(queryReq);

        page(page, queryWrapper);

        Page<PaymentsVO> pageVOResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<Payments> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return pageVOResult;
        }

        List<PaymentsVO> vos = records.stream().map(record -> {
            PaymentsVO vo = new PaymentsVO();
            BeanUtil.copyProperties(record, vo);
    
            return vo;
        }).collect(Collectors.toList());

        pageVOResult.setRecords(vos);
        return pageVOResult;
    }

    @Override
    public Page<Payments> page(PaymentsQueryReq queryReq) {
        Page<Payments> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        LambdaQueryWrapper<Payments> queryWrapper = getLambdaQueryWrapper(queryReq);
        page(page, queryWrapper);
        return page;
    }

    @Override
    public List<Payments> listByQueryReq(PaymentsQueryReq queryReq) {
        LambdaQueryWrapper<Payments> queryWrapper = getLambdaQueryWrapper(queryReq);
        List<Payments> listByQueryReq = list(queryWrapper);
        return listByQueryReq;
    }

    @Override
    public Map<Long, Payments> getIdEntityMap(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<Payments> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Payments::getId, ids);
        List<Payments> entities = list(queryWrapper);
        if (CollectionUtils.isEmpty(entities)) {
            return new HashMap<>();
        }

        return entities.stream().collect(Collectors.toMap(Payments::getId, Function.identity(), (v1, v2) -> v1));
    }

    @Override
    public Payments getOneByQueryReq(PaymentsQueryReq queryReq) {
        LambdaQueryWrapper<Payments> queryWrapper = getLambdaQueryWrapper(queryReq);
        queryWrapper.last("LIMIT 1");

        List<Payments> listByQueryReq = list(queryWrapper);
        if (CollectionUtils.isEmpty(listByQueryReq)) {
            return null;
        }

        return listByQueryReq.get(0);
    }

    private LambdaQueryWrapper<Payments> getLambdaQueryWrapper(PaymentsQueryReq queryReq) {
        LambdaQueryWrapper<Payments> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(Objects.nonNull(queryReq.getId()), Payments::getId, queryReq.getId());
        queryWrapper.eq(Objects.nonNull(queryReq.getOrderId()), Payments::getOrderId, queryReq.getOrderId());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getPaymentMethod()), Payments::getPaymentMethod, queryReq.getPaymentMethod());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getPaymentMethodLike()), Payments::getPaymentMethod, queryReq.getPaymentMethodLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getPaymentStatus()), Payments::getPaymentStatus, queryReq.getPaymentStatus());
        queryWrapper.eq(Objects.nonNull(queryReq.getAmount()), Payments::getAmount, queryReq.getAmount());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getTransactionNo()), Payments::getTransactionNo, queryReq.getTransactionNo());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getTransactionNoLike()), Payments::getTransactionNo, queryReq.getTransactionNoLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getPaymentTime()), Payments::getPaymentTime, queryReq.getPaymentTime());
        queryWrapper.eq(Objects.nonNull(queryReq.getCreatedAt()), Payments::getCreatedAt, queryReq.getCreatedAt());
        queryWrapper.eq(Objects.nonNull(queryReq.getUpdatedAt()), Payments::getUpdatedAt, queryReq.getUpdatedAt());
        queryWrapper.eq(Objects.nonNull(queryReq.getRefundStatus()), Payments::getRefundStatus, queryReq.getRefundStatus());
        queryWrapper.eq(Objects.nonNull(queryReq.getRefundTime()), Payments::getRefundTime, queryReq.getRefundTime());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getDescription()), Payments::getDescription, queryReq.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getDescriptionLike()), Payments::getDescription, queryReq.getDescriptionLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getValidFlag()), Payments::getValidFlag, queryReq.getValidFlag());

        return queryWrapper;
    }

}
