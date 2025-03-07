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
        'red': '#A40F0F',
      },
      fontFamily: {
        roboto: ['Roboto', 'sans-serif'],
        inter: ['Inter', 'sans-serif'],
      },
      screens: {
        'desktop': '1024px',
      },
    },
  },
  plugins: [],
}
