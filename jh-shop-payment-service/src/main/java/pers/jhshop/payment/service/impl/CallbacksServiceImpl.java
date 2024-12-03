package pers.jhshop.payment.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pers.jhshop.payment.model.req.CallbacksCreateReq;
import pers.jhshop.payment.model.req.CallbacksQueryReq;
import pers.jhshop.payment.model.req.CallbacksUpdateReq;
import pers.jhshop.payment.model.vo.CallbacksVO;
import pers.jhshop.payment.model.entity.Callbacks;
import pers.jhshop.payment.mapper.CallbacksMapper;
import pers.jhshop.payment.service.ICallbacksService;
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
 * 支付回调表 服务实现类
 * </p>
 *
 * @author ChenJiahao(wutiao)
 * @since 2024-12-03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CallbacksServiceImpl extends ServiceImpl<CallbacksMapper, Callbacks> implements ICallbacksService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBiz(CallbacksCreateReq createReq) {


        Callbacks entity = new Callbacks();
        BeanUtil.copyProperties(createReq, entity);

        boolean insertResult = entity.insert();

        if (!insertResult) {
            throw new ServiceException("数据插入失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBiz(CallbacksUpdateReq updateReq) {

        // 1.入参有效性判断
        if (Objects.isNull(updateReq.getId())) {
            throw new ServiceException("id不能为空");
        }

        Callbacks entity = getById(updateReq.getId());
        if (Objects.isNull(entity)) {
            throw new ServiceException("支付回调表不存在");
        }

        // 2.重复性判断
        Callbacks entityToUpdate = new Callbacks();
        BeanUtil.copyProperties(updateReq, entityToUpdate);

        boolean updateResult = entityToUpdate.updateById();
        if (!updateResult) {
            throw new ServiceException("数据更新失败");
        }
    }

    @Override
    public CallbacksVO getByIdBiz(Long id) {
        // 1.入参有效性判断
        if (Objects.isNull(id)) {
            throw new ServiceException("id不能为空");
        }

        // 2.存在性判断
        Callbacks entity = getById(id);
        if (Objects.isNull(entity)) {
            throw new ServiceException("支付回调表不存在");
        }

        CallbacksVO vo = new CallbacksVO();
        BeanUtil.copyProperties(entity, vo);

            return vo;
    }

    @Override
    public Page<CallbacksVO> pageBiz(CallbacksQueryReq queryReq) {
        Page<Callbacks> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        page.addOrder(OrderItem.desc("id"));

        LambdaQueryWrapper<Callbacks> queryWrapper = getLambdaQueryWrapper(queryReq);

        page(page, queryWrapper);

        Page<CallbacksVO> pageVOResult = new Page<>(page.getCurrent(), page.getSize(), page.getTotal());
        List<Callbacks> records = page.getRecords();
        if (CollectionUtils.isEmpty(records)) {
            return pageVOResult;
        }

        List<CallbacksVO> vos = records.stream().map(record -> {
            CallbacksVO vo = new CallbacksVO();
            BeanUtil.copyProperties(record, vo);
    
            return vo;
        }).collect(Collectors.toList());

        pageVOResult.setRecords(vos);
        return pageVOResult;
    }

    @Override
    public Page<Callbacks> page(CallbacksQueryReq queryReq) {
        Page<Callbacks> page = new Page<>(queryReq.getCurrent(), queryReq.getSize());
        LambdaQueryWrapper<Callbacks> queryWrapper = getLambdaQueryWrapper(queryReq);
        page(page, queryWrapper);
        return page;
    }

    @Override
    public List<Callbacks> listByQueryReq(CallbacksQueryReq queryReq) {
        LambdaQueryWrapper<Callbacks> queryWrapper = getLambdaQueryWrapper(queryReq);
        List<Callbacks> listByQueryReq = list(queryWrapper);
        return listByQueryReq;
    }

    @Override
    public Map<Long, Callbacks> getIdEntityMap(List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }

        LambdaQueryWrapper<Callbacks> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.in(Callbacks::getId, ids);
        List<Callbacks> entities = list(queryWrapper);
        if (CollectionUtils.isEmpty(entities)) {
            return new HashMap<>();
        }

        return entities.stream().collect(Collectors.toMap(Callbacks::getId, Function.identity(), (v1, v2) -> v1));
    }

    @Override
    public Callbacks getOneByQueryReq(CallbacksQueryReq queryReq) {
        LambdaQueryWrapper<Callbacks> queryWrapper = getLambdaQueryWrapper(queryReq);
        queryWrapper.last("LIMIT 1");

        List<Callbacks> listByQueryReq = list(queryWrapper);
        if (CollectionUtils.isEmpty(listByQueryReq)) {
            return null;
        }

        return listByQueryReq.get(0);
    }

    private LambdaQueryWrapper<Callbacks> getLambdaQueryWrapper(CallbacksQueryReq queryReq) {
        LambdaQueryWrapper<Callbacks> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(Objects.nonNull(queryReq.getId()), Callbacks::getId, queryReq.getId());
        queryWrapper.eq(Objects.nonNull(queryReq.getPaymentId()), Callbacks::getPaymentId, queryReq.getPaymentId());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getCallbackData()), Callbacks::getCallbackData, queryReq.getCallbackData());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getCallbackDataLike()), Callbacks::getCallbackData, queryReq.getCallbackDataLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getCallbackStatus()), Callbacks::getCallbackStatus, queryReq.getCallbackStatus());
        queryWrapper.eq(Objects.nonNull(queryReq.getCreatedAt()), Callbacks::getCreatedAt, queryReq.getCreatedAt());
        queryWrapper.eq(StringUtils.isNotBlank(queryReq.getDescription()), Callbacks::getDescription, queryReq.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(queryReq.getDescriptionLike()), Callbacks::getDescription, queryReq.getDescriptionLike());
        queryWrapper.eq(Objects.nonNull(queryReq.getValidFlag()), Callbacks::getValidFlag, queryReq.getValidFlag());

        return queryWrapper;
    }

}
