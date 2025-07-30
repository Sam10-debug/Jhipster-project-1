package com.emorinken.order.service.impl;

import com.emorinken.order.client.BookAppClient;
import com.emorinken.order.domain.Order;
import com.emorinken.order.repository.OrderRepository;
import com.emorinken.order.service.OrderService;
import com.emorinken.order.service.dto.BookDTO;
import com.emorinken.order.service.dto.OrderDTO;
import com.emorinken.order.service.dto.RequestDTO;
import com.emorinken.order.service.mapper.OrderMapper;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.emorinken.order.web.rest.errors.BadRequestAlertException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;
    private final BookAppClient bookAppClient;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, BookAppClient bookAppClient) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.bookAppClient = bookAppClient;
    }

    @Override
    public OrderDTO save(RequestDTO requestDto) {
        LOG.debug("Request to save Order : {}", requestDto);

            BookDTO book = bookAppClient.getBookByIsbn(requestDto.getBookIsbn());
            OrderDTO orderDTO= new OrderDTO();
            orderDTO.setQuantity(requestDto.getQuantity());
            orderDTO.setTotalPrice(book.getPrice().multiply(BigDecimal.valueOf(orderDTO.getQuantity())));
            orderDTO.setOrderDate(requestDto.getOrderDate());
            orderDTO.setBookIsbn(requestDto.getBookIsbn());
            orderDTO.setCustomerName(requestDto.getCustomerName());
            Order order = orderMapper.toEntity(orderDTO);
            order = orderRepository.save(order);
            return orderMapper.toDto(order);


    }

    @Override
    public OrderDTO update(OrderDTO orderDTO) {
        LOG.debug("Request to update Order : {}", orderDTO);
        Order order = orderMapper.toEntity(orderDTO);
        order = orderRepository.save(order);
        return orderMapper.toDto(order);
    }

    @Override
    public Optional<OrderDTO> partialUpdate(OrderDTO orderDTO) {
        LOG.debug("Request to partially update Order : {}", orderDTO);

        return orderRepository
            .findById(orderDTO.getId())
            .map(existingOrder -> {
                orderMapper.partialUpdate(existingOrder, orderDTO);

                return existingOrder;
            })
            .map(orderRepository::save)
            .map(orderMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> findAll() {
        LOG.debug("Request to get all Orders");
        return orderRepository.findAll().stream().map(orderMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OrderDTO> findOne(Long id) {
        LOG.debug("Request to get Order : {}", id);
        return orderRepository.findById(id).map(orderMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Order : {}", id);
        orderRepository.deleteById(id);
    }
}
