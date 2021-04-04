# pull official base image
FROM node:13.12.0-alpine

# set working directory
RUN mkdir /app
WORKDIR /app

# add `/app/node_modules/.bin` to $PATH
# ENV PATH /app/node_modules/.bin:$PATH

# install app dependencies
COPY /frontend/package.json /app/
COPY /frontend/package-lock.json /app/
RUN npm install --silent
RUN npm install react-scripts@3.4.1 -g --silent

# add app
# COPY /frontend/. /app/


# start app
# CMD ["npm", "start"]