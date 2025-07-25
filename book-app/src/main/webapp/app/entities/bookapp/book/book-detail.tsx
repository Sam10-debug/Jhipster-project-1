import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './book.reducer';

export const BookDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const bookEntity = useAppSelector(state => state.bookapp.book.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="bookDetailsHeading">
          <Translate contentKey="bookappApp.bookappBook.detail.title">Book</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{bookEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="bookappApp.bookappBook.title">Title</Translate>
            </span>
          </dt>
          <dd>{bookEntity.title}</dd>
          <dt>
            <span id="author">
              <Translate contentKey="bookappApp.bookappBook.author">Author</Translate>
            </span>
          </dt>
          <dd>{bookEntity.author}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="bookappApp.bookappBook.price">Price</Translate>
            </span>
          </dt>
          <dd>{bookEntity.price}</dd>
          <dt>
            <span id="isbn">
              <Translate contentKey="bookappApp.bookappBook.isbn">Isbn</Translate>
            </span>
          </dt>
          <dd>{bookEntity.isbn}</dd>
        </dl>
        <Button tag={Link} to="/bookapp/book" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/bookapp/book/${bookEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BookDetail;
