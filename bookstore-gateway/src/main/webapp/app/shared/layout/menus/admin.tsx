import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';

import { Translate, translate } from 'react-jhipster';
import { NavDropdown } from './menu-components';

const adminMenuItems = () => (
  <>
    <MenuItem icon="road" to="/admin/gateway">
      <Translate contentKey="global.menu.admin.gateway">Gateway</Translate>
    </MenuItem>
    <MenuItem icon="users" to="/admin/user-management">
      <Translate contentKey="global.menu.admin.userManagement">User management</Translate>
    </MenuItem>
    {/* jhipster-needle-add-element-to-admin-menu - JHipster will add entities to the admin menu here */}
  </>
);

const openAPIItem = () => (
  <MenuItem icon="book" to="/admin/docs">
    <Translate contentKey="global.menu.admin.apidocs">API</Translate>
  </MenuItem>
);

export const AdminMenu = ({ showOpenAPI }) => (
  <NavDropdown icon="users-cog" name={translate('global.menu.admin.main')} id="admin-menu" data-cy="adminMenu">
    {adminMenuItems()}
    {showOpenAPI && openAPIItem()}
  </NavDropdown>
);

export default AdminMenu;
