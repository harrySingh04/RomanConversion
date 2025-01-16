import React , {useState} from 'react';

import {
  createTheme,
    ThemeProvider,
    CssBaseline,
    Container,
    Box,
    TextField,
    Button,
    Typography,
    Switch,
    FormControlLabel
} from "@mui/material";

function App() {

  // State to store the input value entered by the user
  const [inputValue , setInputValue] = useState('');
  // State to store the converted Roman numeral
  const [romanNumeral , setRomanNumeral] = useState('');
  // State to toggle between light and dark mode
  const [isDarkMode, setIsDarkMode] = useState(false);

  // Function to handle API requests for converting a number to Roman numeral
  const convertToRoman = async () =>{

    // Input validation: Ensure the user enters a valid number
    if(!inputValue || isNaN(inputValue)){
      setRomanNumeral('Please enter a valid number');
      return;
    }

    try{

      // Fetch the Roman numeral from the backend API
      const response = await fetch(`http://localhost:8080/romannumeral?query=${inputValue}`)

      // Parse the API response
      const data = await response.json();

      // Update the Roman numeral result from the API response
      // If the API returns an invalid number, display a default message
      setRomanNumeral(data.value || 'Invalid number')
    }
    catch(error){
      // Handle any errors during the API call
      setRomanNumeral('Error fetching the Roman numeral')
    }
  };

  // Theme configuration for dark/light mode using Material UI's ThemeProvider
  const theme = createTheme({
    palette: {
      mode: isDarkMode ? 'dark' : 'light',
    },
  });


  // The main layout of the application
  return (
      <ThemeProvider theme={theme}>
        <CssBaseline /> {/* Ensures consistent styling for dark/light modes */}
        <Container
            maxWidth={'sm'}
            sx={{
              textAlign: 'center',
              marginTop: 4,
              display: 'flex',
              flexDirection: 'column', // Align all items in a vertical column
              alignItems: 'center', // Center items horizontally
            }}
        >
          {/* App Title */}
          <Typography variant={'h5'} gutterBottom fontWeight={'bold'}>
            Roman Numeral Converter
          </Typography>

          {/* Input Section */}
          <Box sx={{
              display: 'flex',
              flexDirection: 'column', // Align all child components vertically
              alignItems: 'left', // Center align items horizontally
              width: '60%', // Ensure full width for input and button
              maxWidth: '300px', // Set a maximum width for better design
              marginBottom: 4,
          }}>
            {/* Label above the input text field */}
            <Typography
                variant={'subtitle1'}
                sx={{ marginRight: 2 ,marginLeft: -20}}
            >
              Enter a number
            </Typography>

            {/* TextField for user input */}
            <TextField
                variant={'outlined'}
                fullWidth
                value={inputValue}
                onChange={(e) => setInputValue(e.target.value)}
                sx={{ marginBottom: 2 }}
            />

            {/* Convert Button */}
            <Button
                onClick={convertToRoman}
                fullWidth
                sx={{
                    marginBottom: 2,
                    border: `2px solid ${isDarkMode ? 'white' : 'black'}`, // Border color switches dynamically
                    color: `${isDarkMode ? 'white' : 'black'}`, // Text color switches dynamically
                    borderRadius: '16px', // Curved corners
                    backgroundColor: 'transparent', // No background color
                    textTransform: 'none', // Keep button text as is (no uppercase)
                    fontWeight: 'bold', // Bold text
                    '&:hover': {
                        backgroundColor: `${isDarkMode ? 'rgba(255, 255, 255, 0.1)' : 'rgba(0, 0, 0, 0.1)'}`, // Hover effect adapts
                    },
                }}
            >
              Convert to Roman Numeral
            </Button>

          {/* Result Section */}
          <Box>
            {/* Displaying Roman numeral result on one line */}
              <Typography variant={'subtitle1'} textAlign={"left"}>
                <span
                    style={{
                        fontWeight: 'bold'
                    }}
                >
                    Roman Numeral:</span>
              <span
                  style={{
                      marginLeft: '8px', // Add spacing between label and result
                  }}
              >
              {romanNumeral}
            </span>
              </Typography>
          </Box>
          </Box>
            {/* Toggle switch for Dark Mode */}
            <Box sx={{marginBottom: 2}}>
                <FormControlLabel
                    control={
                        <Switch
                            checked={isDarkMode}
                            onChange={(e) => setIsDarkMode(e.target.checked)}
                        />
                    }
                    label={'Dark Mode'}
                />
            </Box>
        </Container>

      </ThemeProvider>
  );
}

export default App;
