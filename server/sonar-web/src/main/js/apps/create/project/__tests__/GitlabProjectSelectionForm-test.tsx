/*
 * SonarQube
 * Copyright (C) 2009-2020 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import { shallow } from 'enzyme';
import * as React from 'react';
import { mockGitlabProject } from '../../../../helpers/mocks/alm-integrations';
import GitlabProjectSelectionForm, {
  GitlabProjectSelectionFormProps
} from '../GitlabProjectSelectionForm';

it('should render correctly', () => {
  expect(shallowRender()).toMatchSnapshot('projects');

  expect(shallowRender({ projects: undefined, projectsPaging: mockPaging() })).toMatchSnapshot(
    'undefined projects'
  );
  expect(shallowRender({ projects: [], projectsPaging: mockPaging() })).toMatchSnapshot(
    'no projects'
  );
  expect(
    shallowRender({ projects: [], projectsPaging: mockPaging(), searchQuery: 'findme' })
  ).toMatchSnapshot('no projects when searching');
});

function shallowRender(props: Partial<GitlabProjectSelectionFormProps> = {}) {
  const projects = [
    mockGitlabProject(),
    mockGitlabProject({
      id: '2',
      sqProjectKey: 'already-imported',
      sqProjectName: 'Already Imported'
    })
  ];

  return shallow<GitlabProjectSelectionFormProps>(
    <GitlabProjectSelectionForm
      loadingMore={false}
      onLoadMore={jest.fn()}
      onSearch={jest.fn()}
      projects={projects}
      projectsPaging={mockPaging(projects.length)}
      searching={false}
      searchQuery=""
      {...props}
    />
  );
}

function mockPaging(total = 0) {
  return { total, pageIndex: 1, pageSize: 30 };
}
