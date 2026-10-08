FROM node:22 AS build

WORKDIR /app

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build

FROM nginx:alpine

RUN rm -rf /usr/share/nginx/html/*

# ✅ Correct
COPY --from=build /app/dist/training_trading/browser/ /usr/share/nginx/html/

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80