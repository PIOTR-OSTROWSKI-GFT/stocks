import React from 'react';
import { render } from '@testing-library/react';
import App from './App';

test('renders stock table', () => {
  const { getByText } = render(<App />);
  const symbolColumn = getByText(/Symbol/i);
  expect(symbolColumn).toBeInTheDocument();
});
