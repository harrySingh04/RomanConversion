import { render, screen , fireEvent } from '@testing-library/react';
import '@testing-library/jest-dom';
import App from './App';

describe('Roman Numeral Converter App', () => {
  it('renders the title and input field', () => {
    // Render the app
    render(<App />);

    // Check for the title
    const title = screen.getByText(/Roman Numeral Converter/i);
    expect(title).toBeInTheDocument();

    // Check for the input field
    const inputField = screen.getByLabelText(/Enter the number here.../i);
    expect(inputField).toBeInTheDocument();
  });

  it('focuses the input field on load', () => {
    // Render the app
    render(<App />);

    // Check if the input field is focused
    const inputField = screen.getByLabelText(/Enter the number here.../i);
    expect(inputField).toHaveFocus();
  });

  it('allows users to input a number', () => {
    // Render the app
    render(<App />);

    // Find the input field and type a number
    const inputField = screen.getByLabelText(/Enter the number here.../i);
    fireEvent.change(inputField, { target: { value: '1994' } });

    // Check if the input value is updated
    expect(inputField.value).toBe('1994');
  });

  it('displays the result when the "Convert to Roman Numeral" button is clicked',  async () => {
    // Mock the API call for invalid input
    global.fetch = jest.fn(() =>
        Promise.resolve({
          json: () => Promise.resolve({ output: 'MCMXCIV' }),
        })
    );

    // Render the app
    render(<App />);

    // Find the input field and type an invalid number
    const inputField = screen.getByLabelText(/Enter the number here.../i);
    fireEvent.change(inputField, { target: { value: '1994' } });

    // Find the button and click it
    const convertButton = screen.getByRole('button', { name: /Convert to Roman Numeral/i });
    fireEvent.click(convertButton);

    // Wait for the error message to appear
    const errorMessage = await screen.findByText(/MCMXCIV/i);
    expect(errorMessage).toBeInTheDocument();

    // Cleanup the mock
    global.fetch.mockRestore();
  });

  it('shows an error message for invalid inputs', async () => {
    // Mock the API call for invalid input
    global.fetch = jest.fn(() =>
        Promise.resolve({
          json: () => Promise.resolve({ errorMsg: 'Invalid number' }),
        })
    );

    // Render the app
    render(<App />);

    // Find the input field and type an invalid number
    const inputField = screen.getByLabelText(/Enter the number here.../i);
    fireEvent.change(inputField, { target: { value: '0' } });

    // Find the button and click it
    const convertButton = screen.getByRole('button', { name: /Convert to Roman Numeral/i });
    fireEvent.click(convertButton);

    // Wait for the error message to appear
    const errorMessage = await screen.findByText(/Invalid number/i);
    expect(errorMessage).toBeInTheDocument();

    // Cleanup the mock
    global.fetch.mockRestore();
  });

  it('toggles dark mode when the switch is clicked', () => {
    // Render the app
    render(<App />);

    // Find the dark mode toggle
    const darkModeSwitch = screen.getByRole('checkbox', { name: /Dark Mode/i });

    // Initially, dark mode should be off
    expect(darkModeSwitch).not.toBeChecked();

    // Toggle dark mode on
    fireEvent.click(darkModeSwitch);
    expect(darkModeSwitch).toBeChecked();

    // Toggle dark mode off
    fireEvent.click(darkModeSwitch);
    expect(darkModeSwitch).not.toBeChecked();
  });

  it('changes button styles when dark mode is toggled', () => {
    // Render the app
    render(<App />);

    // Find the dark mode toggle and the convert button
    const darkModeSwitch = screen.getByRole('checkbox', { name: /Dark Mode/i });
    const convertButton = screen.getByRole('button', { name: /Convert to Roman Numeral/i });

    // Initially, the button's border and text color should be black
    expect(convertButton).toHaveStyle('border: 2px solid black');
    expect(convertButton).toHaveStyle('color: black');

    // Toggle dark mode on
    fireEvent.click(darkModeSwitch);

    // Now, the button's border and text color should be white
    expect(convertButton).toHaveStyle('border: 2px solid white');
    expect(convertButton).toHaveStyle('color: white');
  });
});