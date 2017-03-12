class DepartmentsController < ApplicationController
	def index
		render json: Department.all
	end

	def create
		d = Department.new
		d.name = params[:dept_name]
		d.save
		render json: d
	end
end
