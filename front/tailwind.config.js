/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{html,ts}",
  ],
  theme: {
    extend: {
      colors: {
        'purple': '#7763C5',
        'grey': '#F5F5F5',
        'dark-grey': '#D9D9D9',
        'light-grey': '#EEEEEE',
        'red': '#A40F0F',
      },
      fontFamily: {
        roboto: ['Roboto', 'sans-serif'],
        inter: ['Inter', 'sans-serif'],
      },
      fontSize: {
        '3xl': '24px',
        '2xl': '20px',
        'xl': '16px',
        'md': '14px',
        'sm': '12px',
      },
      fontWeight: {
        'light': 300,
        'normal': 400,
        'medium': 500,
        'bold': 700,
      },
      borderRadius: {
        'md:': '8px',
        'lg': '16px',
      },
      screens: {
        'desktop': '1024px',
      },
    },
  },
  plugins: [],
}
