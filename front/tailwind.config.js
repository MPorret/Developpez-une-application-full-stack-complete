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
      }
    },
  },
  plugins: [],
}
